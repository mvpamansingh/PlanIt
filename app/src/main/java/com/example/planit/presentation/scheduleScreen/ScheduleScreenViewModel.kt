package com.example.planit.presentation.scheduleScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.domain.repository.RoomRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val repository: RoomRepository
) : ViewModel() {

    private val _state = mutableStateOf(ScheduleState())
    val state: State<ScheduleState> = _state

    init {
        loadTasksForSelectedDate()
    }

    fun onEvent(event: ScheduleEvent) {
        when (event) {
            is ScheduleEvent.OnDateSelected -> {
                _state.value = _state.value.copy(selectedDate = event.date)
                loadTasksForSelectedDate()
            }
            ScheduleEvent.LoadTasks -> {
                loadTasksForSelectedDate()
            }

            is ScheduleEvent.OnTaskStatusChanged -> {
                updateTaskStatusChanged(event.taskId,event.isCompleted)
            }
        }
    }

    private fun loadTasksForSelectedDate() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                repository.getTasksForDate(_state.value.selectedDate).collect { tasks ->
                    _state.value = _state.value.copy(
                        tasks = tasks.sortedBy { task -> task.startTime },
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

    private fun updateTaskStatusChanged(taskId:Long, isCompleted:Boolean)
    {
        viewModelScope.launch {

            try {
                val task = repository.getTaskById(taskId)
                task?.let {
                    val updatedTask = it.copy(isCompleted = isCompleted)
                    repository.updateTask(updatedTask)
                }
            }
            catch (e:Exception)
            {

                _state.value=  _state.value.copy(
                    error = e.message
                )

            }

        }
    }
}