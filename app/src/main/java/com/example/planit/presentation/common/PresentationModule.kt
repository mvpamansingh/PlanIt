package com.example.planit.presentation.common

import com.example.planit.presentation.createTask.CreateTaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val presentationModule = module {
    viewModel {
        CreateTaskViewModel(get())
    }
}