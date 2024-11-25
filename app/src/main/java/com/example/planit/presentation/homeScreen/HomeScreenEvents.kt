package com.example.planit.presentation.homeScreen


sealed class HomeScreenEvents{

    data class SearchStateChanged(val search:String):HomeScreenEvents()
    data object OnHighPriorityClicked:HomeScreenEvents()
    data object OnLowPriorityClicked:HomeScreenEvents()
    data object OnMediumPriorityClicked:HomeScreenEvents()
    data object OnClearPriorityFilter: HomeScreenEvents()
    data class OnTaskStatusChanged(val taskId: Long, val isCompleted: Boolean): HomeScreenEvents()
    data object RefreshTasks: HomeScreenEvents()
}