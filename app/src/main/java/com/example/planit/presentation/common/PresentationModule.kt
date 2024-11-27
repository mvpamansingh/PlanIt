package com.example.planit.presentation.common

import com.example.planit.presentation.createTask.CreateTaskViewModel
import com.example.planit.presentation.homeScreen.HomeScreen
import com.example.planit.presentation.homeScreen.HomeScreenViewModel
import com.example.planit.presentation.priorityScreen.LowPriority.LowPriorityViewModel
import com.example.planit.presentation.priorityScreen.MediumPriority.MediumPriorityViewModel
import com.example.planit.presentation.priorityScreen.highPriority.HighPriorityViewModel
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

    viewModel{
        HomeScreenViewModel()
    }

    viewModel {
        HighPriorityViewModel()
    }



    viewModel {
        LowPriorityViewModel()
    }

    viewModel {
        MediumPriorityViewModel()
    }
}