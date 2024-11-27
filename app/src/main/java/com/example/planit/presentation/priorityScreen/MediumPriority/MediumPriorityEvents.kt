package com.example.planit.presentation.priorityScreen.MediumPriority


sealed class MediumPriorityEvents{

    data class OnTaskClicked(val taskId:Long):MediumPriorityEvents()
    data class OnTaskStatusChanged(val taskId: Long, val isCompleted: Boolean):MediumPriorityEvents()
    data class SearchStateChanged(val search:String):MediumPriorityEvents()
}