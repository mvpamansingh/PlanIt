package com.example.planit.presentation.priorityScreen.LowPriority


sealed class LowPriorityEvents{

    data class OnTaskClicked(val taskId:Long):LowPriorityEvents()
    data class OnTaskStatusChanged(val taskId: Long, val isCompleted: Boolean):LowPriorityEvents()
    data class SearchStateChanged(val search:String):LowPriorityEvents()
}