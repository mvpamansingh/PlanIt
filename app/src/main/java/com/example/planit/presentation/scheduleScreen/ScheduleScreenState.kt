package com.example.planit.presentation.scheduleScreen

import com.example.planit.data.local.entity.TaskEntity


data class ScheduleState(
    val selectedDate: Long = System.currentTimeMillis(),
    val tasks: List<TaskEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)