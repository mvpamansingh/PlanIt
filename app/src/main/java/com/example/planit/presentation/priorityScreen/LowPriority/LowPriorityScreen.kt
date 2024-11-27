@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.planit.presentation.priorityScreen.LowPriority

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
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import java.util.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import com.example.planit.presentation.scheduleScreen.TaskCard

@Composable
fun LowPriorityScreen(
    viewModel: LowPriorityViewModel = koinViewModel(),
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
                onValueChange = { viewModel.OnEvent(LowPriorityEvents.SearchStateChanged(it)) },
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
                            viewModel.OnEvent(LowPriorityEvents.OnTaskStatusChanged(taskId = task.id, isCompleted= isCompleted))
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
                    text = "Low Priority Task",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Be chill low pri",
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


