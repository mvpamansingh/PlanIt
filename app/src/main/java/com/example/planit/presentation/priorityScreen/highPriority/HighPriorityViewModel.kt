package com.example.planit.presentation.priorityScreen.highPriority

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.data.local.entity.Priority
import com.example.planit.data.local.entity.TaskEntity
import com.example.planit.domain.repository.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar

class HighPriorityViewModel():ViewModel(), KoinComponent {

    private val repository :RoomRepository by inject()
    private val _state = MutableStateFlow(HighPriorityState())
    val state = _state.asStateFlow()

    init {
        loadTodaysTasks()
    }

    fun OnEvent(events: HighPriorityEvents)
    {
        when(events)
        {
            is HighPriorityEvents.OnTaskClicked -> {
                TODO()
            }
            is HighPriorityEvents.OnTaskStatusChanged -> {
                updateTaskStatus(events.taskId, events.isCompleted)
            }

            is HighPriorityEvents.SearchStateChanged -> {
                _state.value = _state.value.copy(searchState = events.search)
                filterTasks()
            }
        }
    }



    private fun updateTaskStatus(taskID : Long, completed:Boolean)
    {
        viewModelScope.launch {

            try{
                val task =repository.getTaskById(taskID)
                task?.let {

                    val updatedTask = it.copy(isCompleted = completed)
                    repository.updateTask(updatedTask)
                    loadTodaysTasks()

                }
            }
            catch (e:Exception)
            {
                _state.value= _state.value.copy(error = e.message)
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


                    // Calculate total tasks and completed tasks
                   // val totalTasks = tasks.size
                    val highPrioritySize = highPriorityTasks.size
                    val completedTasks = highPriorityTasks.count { it.isCompleted }
                    val highPrioritytaskProgress = if (highPrioritySize > 0) {
                        (completedTasks * 100) / highPrioritySize
                    } else 0

                    _state.value = _state.value.copy(
                        tasks = highPriorityTasks,

                        highPriorityTasks = highPrioritySize,

                        highPriorityCompleted = completedTasks,
                        highPriorityProgress = highPrioritytaskProgress,

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

//    private fun filterTasks() {
//        viewModelScope.launch {
//            try {
//                if (_state.value.selectedPriority != null) {
//                    repository.getTasksByPriority(_state.value.selectedPriority!!).collect { tasks ->
//                        val filteredTasks = tasks.filter { task ->
//                            task.title.contains(_state.value.searchState, ignoreCase = true) ||
//                                    task.description.contains(_state.value.searchState, ignoreCase = true)
//                        }
//                        _state.value = _state.value.copy(tasks = filteredTasks)
//                    }
//                } else {
//                    repository.getAllTasksByPriority().collect { tasks ->
//                        val filteredTasks = tasks.filter { task ->
//                            task.title.contains(_state.value.searchState, ignoreCase = true) ||
//                                    task.description.contains(_state.value.searchState, ignoreCase = true)
//                        }
//                        _state.value = _state.value.copy(tasks = filteredTasks)
//                    }
//                }
//            } catch (e: Exception) {
//                _state.value = _state.value.copy(error = e.message)
//            }
//        }
//    }
private fun filterTasks() {
    viewModelScope.launch {
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

            repository.getTodayTasksByPriority(startOfDay, endOfDay).collect { allTasks ->
                val highPriorityTasks = allTasks.filter { it.priority == Priority.HIGH }
                val filteredTasks = highPriorityTasks.filter { task ->
                    task.title.contains(_state.value.searchState, ignoreCase = true) ||
                            task.description.contains(_state.value.searchState, ignoreCase = true)
                }

                val completedTasks = filteredTasks.count { it.isCompleted }
                val totalTasks = filteredTasks.size
                val progress = if (totalTasks > 0) {
                    (completedTasks * 100) / totalTasks
                } else 0

                _state.value = _state.value.copy(
                    tasks = filteredTasks,
                    highPriorityTasks = totalTasks,
                    highPriorityCompleted = completedTasks,
                    highPriorityProgress = progress,
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            _state.value = _state.value.copy(error = e.message)
        }
    }
}
}


