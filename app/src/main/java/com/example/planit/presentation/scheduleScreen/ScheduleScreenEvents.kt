package com.example.planit.presentation.scheduleScreen



sealed class ScheduleEvent {
    data class OnDateSelected(val date: Long) : ScheduleEvent()
    object LoadTasks : ScheduleEvent()
}