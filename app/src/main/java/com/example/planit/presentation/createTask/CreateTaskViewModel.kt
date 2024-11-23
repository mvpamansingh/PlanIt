package com.example.planit.presentation.createTask

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.data.local.entity.TaskEntity
import com.example.planit.domain.repository.RoomRepository
import kotlinx.coroutines.launch

class CreateTaskViewModel(
    private val repository: RoomRepository
) : ViewModel() {

    private val _state = mutableStateOf(CreateTaskState())
    val state = _state

    fun onEvent(event: CreateTaskEvent) {
        when (event) {
            is CreateTaskEvent.OnTitleChange -> {
                _state.value = _state.value.copy(title = event.title)
            }
            is CreateTaskEvent.OnDescriptionChange -> {
                _state.value = _state.value.copy(description = event.description)
            }
            is CreateTaskEvent.OnStartTimeChange -> {
                _state.value = _state.value.copy(startTime = event.startTime)
            }
            is CreateTaskEvent.OnEndTimeChange -> {
                _state.value = _state.value.copy(endTime = event.endTime)
            }
            is CreateTaskEvent.OnDateChange -> {
                _state.value = _state.value.copy(date = event.date)
            }
            is CreateTaskEvent.OnPriorityChange -> {
                _state.value = _state.value.copy(priority = event.priority)
            }
            is CreateTaskEvent.OnCreateTask -> {
                createTask()
            }
        }
    }

    private fun createTask() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)

                val task = TaskEntity(
                    title = _state.value.title,
                    description = _state.value.description,
                    startTime = _state.value.startTime,
                    endTime = _state.value.endTime,
                    date = _state.value.date,
                    priority = _state.value.priority
                )

                repository.insertTask(task)
                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}