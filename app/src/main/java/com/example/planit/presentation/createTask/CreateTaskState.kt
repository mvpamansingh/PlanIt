package com.example.planit.presentation.createTask

import com.example.planit.data.local.entity.Priority


data class CreateTaskState(
    val title: String = "",
    val description: String = "",
    val startTime: Long = System.currentTimeMillis(),
    val endTime: Long = System.currentTimeMillis(),
    val date: Long = System.currentTimeMillis(),
    val priority: Priority = Priority.MEDIUM,
    val isLoading: Boolean = false,
    val error: String? = null
)