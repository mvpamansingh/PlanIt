package com.example.planit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.planit.presentation.common.AdvancedTimePicker
import com.example.planit.presentation.createTask.CreateTaskScreen
import com.example.planit.presentation.createTask.CreateTaskScreenV1
import com.example.planit.presentation.homeScreen.HomeScreen
import com.example.planit.presentation.homeScreen.HomeScreenViewModel
import com.example.planit.presentation.scheduleScreen.ScheduleScreen
import com.example.planit.presentation.test.TaskListScreen
import com.example.planit.ui.theme.PlanItTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanItTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                  // CreateTaskScreenV1 {  }
               // AdvancedTimePicker(onDismiss = {}, onConfirm = {})
                    //TaskListScreen {  }
                    //ScheduleScreen {  }

                    val viewModel :HomeScreenViewModel = koinViewModel()
                    HomeScreen(viewModel, onNavigateToCreateTask = {}) { }
                }
            }
        }
    }
}








// a
