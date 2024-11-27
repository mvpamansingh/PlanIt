package com.example.planit.presentation.priorityScreen.MediumPriority

import com.example.planit.data.local.entity.TaskEntity


data class MediumPriorityState(
    val tasks:List<TaskEntity> = emptyList(),
    val searchState :String= "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val highPriorityCompleted: Int = 0,
    val highPriorityTasks: Int = 0,
    val highPriorityProgress:Int= 0
)