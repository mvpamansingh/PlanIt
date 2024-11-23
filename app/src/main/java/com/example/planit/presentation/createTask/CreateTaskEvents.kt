package com.example.planit.presentation.createTask

import com.example.planit.data.local.entity.Priority


sealed class CreateTaskEvent {
    data class OnTitleChange(val title: String): CreateTaskEvent()
    data class OnDescriptionChange(val description: String): CreateTaskEvent()
    data class OnStartTimeChange(val startTime: Long): CreateTaskEvent()
    data class OnEndTimeChange(val endTime: Long): CreateTaskEvent()
    data class OnDateChange(val date: Long): CreateTaskEvent()
    data class OnPriorityChange(val priority: Priority): CreateTaskEvent()
    object OnCreateTask: CreateTaskEvent()
}