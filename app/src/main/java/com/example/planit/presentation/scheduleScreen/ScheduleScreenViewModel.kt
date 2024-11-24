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
}