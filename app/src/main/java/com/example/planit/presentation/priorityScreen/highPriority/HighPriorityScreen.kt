@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.planit.presentation.priorityScreen.highPriority

import com.example.planit.presentation.homeScreen.HomeScreenEvents
import com.example.planit.presentation.homeScreen.HomeScreenViewModel
import com.example.planit.presentation.homeScreen.PriorityCards
import com.example.planit.presentation.homeScreen.TaskProgressCard



import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.planit.data.local.entity.Priority
import org.koin.androidx.compose.koinViewModel
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.example.planit.data.local.entity.TaskEntity
import com.example.planit.presentation.common.DateSliderPicker
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.planit.presentation.scheduleScreen.ScheduleEvent
import com.example.planit.presentation.scheduleScreen.TaskCard
import kotlinx.coroutines.launch

@Composable
fun HighPriorityScreen(
    viewModel: HighPriorityViewModel = koinViewModel(),
    onNavigateToCreateTask: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            HomeTopBar(
                onNavigateToNotifications = onNavigateToNotifications
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreateTask) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = state.searchState,
                onValueChange = { viewModel.OnEvent(HighPriorityEvents.SearchStateChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search task") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Progress Section
            TaskProgressCard(
                progress = state.highPriorityProgress,
                completedTasks = state.highPriorityCompleted,
                totalTasks = state.highPriorityTasks
            )

            Spacer(modifier = Modifier.height(24.dp))



            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Urgent tasks",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tasks List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.tasks) { task ->
                    TaskCard(
                        task = task, onTaskComplete = { isCompleted->
                            viewModel.OnEvent(HighPriorityEvents.OnTaskStatusChanged(taskId = task.id, isCompleted= isCompleted))
                        }

                    )
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    state.error?.let { error ->
        Toast.makeText(LocalContext.current, error, Toast.LENGTH_LONG).show()
    }
}

@Composable
private fun HomeTopBar(
    onNavigateToNotifications: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "High Priority Task",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Be productive today",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        actions = {
            IconButton(onClick = onNavigateToNotifications) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications")
            }
        }
    )
}


@Composable
private fun PriorityFilterChips(
    selectedPriority: Priority?,
    onPrioritySelected: (Priority) -> Unit,
    onClearFilter: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selectedPriority == Priority.HIGH,
            onClick = { onPrioritySelected(Priority.HIGH) },
            label = { Text("High") }
        )
        FilterChip(
            selected = selectedPriority == Priority.MEDIUM,
            onClick = { onPrioritySelected(Priority.MEDIUM) },
            label = { Text("Medium") }
        )
        FilterChip(
            selected = selectedPriority == Priority.LOW,
            onClick = { onPrioritySelected(Priority.LOW) },
            label = { Text("Low") }
        )
        if (selectedPriority != null) {
            FilterChip(
                selected = false,
                onClick = onClearFilter,
                label = { Text("Clear") }
            )
        }
    }
}