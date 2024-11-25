package com.example.planit.presentation.homeScreen

import com.example.planit.data.local.entity.Priority
import com.example.planit.data.local.entity.TaskEntity


data class HomeScreenState(
    val tasks:List<TaskEntity> = emptyList(),
    val searchState :String= "",
    val taskProgress: Int = 0,
    val completedTasks: Int = 0,
    val totalTasks: Int = 0,
    val highPriorityTasks: Int = 0,
    val mediumPriorityTasks: Int = 0,
    val lowPriorityTasks: Int = 0,
    val highPriorityProgress: Int = 0,
    val mediumPriorityProgress: Int = 0,
    val selectedPriority: Priority? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val lowPriorityProgress: Int = 0,
)