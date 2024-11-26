package com.example.planit.presentation.homeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.data.local.entity.Priority
import com.example.planit.data.local.entity.TaskEntity
import com.example.planit.domain.repository.RoomRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar


class HomeScreenViewModel : ViewModel(), KoinComponent {
    private val repository: RoomRepository by inject()

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    init {
        loadTodaysTasks()
    }

    fun onEvent(events: HomeScreenEvents) {
        when(events) {
            HomeScreenEvents.OnHighPriorityClicked -> {
//                _state.value = _state.value.copy(selectedPriority = Priority.HIGH)
//                filterTasks()
            }
            HomeScreenEvents.OnLowPriorityClicked -> {
//                _state.value = _state.value.copy(selectedPriority = Priority.LOW)
//                filterTasks()
            }
            HomeScreenEvents.OnMediumPriorityClicked -> {
//                _state.value = _state.value.copy(selectedPriority = Priority.MEDIUM)
//                filterTasks()
            }
            HomeScreenEvents.OnClearPriorityFilter -> {
                _state.value = _state.value.copy(selectedPriority = null)
                loadTodaysTasks()
            }
            is HomeScreenEvents.SearchStateChanged -> {
                _state.value = _state.value.copy(searchState = events.search)
                filterTasks()
            }
            is HomeScreenEvents.OnTaskStatusChanged -> {
                updateTaskCompletion(events.taskId, events.isCompleted)
            }
            HomeScreenEvents.RefreshTasks -> {
                loadTodaysTasks()
            }
        }
    }




    private fun loadTodaysTasks() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val calendar = Calendar.getInstance()
                val startOfDay = calendar.apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis

                val endOfDay = calendar.apply {
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                    set(Calendar.MILLISECOND, 999)
                }.timeInMillis

                repository.getTodayTasksByPriority(startOfDay, endOfDay).collect { tasks ->
                    val highPriorityTasks = tasks.filter { it.priority == Priority.HIGH }
                    val mediumPriorityTasks = tasks.filter { it.priority == Priority.MEDIUM }
                    val lowPriorityTasks = tasks.filter { it.priority == Priority.LOW }

                    // Calculate total tasks and completed tasks
                    val totalTasks = tasks.size
                    val completedTasks = tasks.count { it.isCompleted }
                    val taskProgress = if (totalTasks > 0) {
                        (completedTasks * 100) / totalTasks
                    } else 0

                    _state.value = _state.value.copy(
                        tasks = tasks,
                        totalTasks = totalTasks,
                        completedTasks = completedTasks,
                        taskProgress = taskProgress,
                        highPriorityTasks = highPriorityTasks.size,
                        mediumPriorityTasks = mediumPriorityTasks.size,
                        lowPriorityTasks = lowPriorityTasks.size,
                        highPriorityProgress = calculateProgress(highPriorityTasks),
                        mediumPriorityProgress = calculateProgress(mediumPriorityTasks),
                        lowPriorityProgress = calculateProgress(lowPriorityTasks),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }



    private fun calculateProgress(tasks: List<TaskEntity>): Int {
        if (tasks.isEmpty()) return 0
        val completedTasks = tasks.count { it.isCompleted }
        return (completedTasks * 100) / tasks.size
    }

    private fun filterTasks() {
        viewModelScope.launch {
            try {
                if (_state.value.selectedPriority != null) {
                    repository.getTasksByPriority(_state.value.selectedPriority!!).collect { tasks ->
                        val filteredTasks = tasks.filter { task ->
                            task.title.contains(_state.value.searchState, ignoreCase = true) ||
                                    task.description.contains(_state.value.searchState, ignoreCase = true)
                        }
                        _state.value = _state.value.copy(tasks = filteredTasks)
                    }
                } else {
                    repository.getAllTasksByPriority().collect { tasks ->
                        val filteredTasks = tasks.filter { task ->
                            task.title.contains(_state.value.searchState, ignoreCase = true) ||
                                    task.description.contains(_state.value.searchState, ignoreCase = true)
                        }
                        _state.value = _state.value.copy(tasks = filteredTasks)
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    private fun updateTaskCompletion(taskId: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            try {
                val task = repository.getTaskById(taskId)
                task?.let {
                    val updatedTask = it.copy(isCompleted = isCompleted)
                    repository.updateTask(updatedTask)
                    loadTodaysTasks()
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}

