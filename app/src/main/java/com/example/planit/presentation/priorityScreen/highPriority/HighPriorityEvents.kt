package com.example.planit.presentation.priorityScreen.highPriority


sealed class HighPriorityEvents{

    data class OnTaskClicked(val taskId:Long):HighPriorityEvents()
    data class OnTaskStatusChanged(val taskId: Long, val isCompleted: Boolean):HighPriorityEvents()
    data class SearchStateChanged(val search:String):HighPriorityEvents()
}