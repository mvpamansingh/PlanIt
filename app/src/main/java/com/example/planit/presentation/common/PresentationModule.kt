package com.example.planit.presentation.common

import com.example.planit.presentation.createTask.CreateTaskViewModel
import com.example.planit.presentation.scheduleScreen.ScheduleViewModel
import com.example.planit.presentation.test.TaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val presentationModule = module {
    viewModel {
        CreateTaskViewModel(get())
    }
    viewModel {
        TaskListViewModel(get())
    }

    viewModel {
        ScheduleViewModel(get())
    }
}