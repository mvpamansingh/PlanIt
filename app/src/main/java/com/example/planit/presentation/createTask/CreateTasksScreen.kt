package com.example.planit.presentation.createTask

import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.planit.data.local.entity.Priority
import org.koin.androidx.compose.koinViewModel
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.planit.presentation.common.AdvancedTimePicker
import com.example.planit.presentation.common.CustomDatePickerDialog
import com.example.planit.presentation.common.DateSliderPicker
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    viewModel: CreateTaskViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = state.title,
            onValueChange = { viewModel.onEvent(CreateTaskEvent.OnTitleChange(it)) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.description,
            onValueChange = { viewModel.onEvent(CreateTaskEvent.OnDescriptionChange(it)) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(8.dp))


        Button(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    .format(Date(state.date))
            )
        }

        if (showDatePicker) {
            CustomDatePickerDialog(
                onDismiss = { showDatePicker = false },
                onConfirm = { date ->
                    viewModel.onEvent(CreateTaskEvent.OnDateChange(date))
                    showDatePicker = false
                },
                selectedDate = state.date
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Time Pickers


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { showStartTimePicker = true }  // Changed to show custom picker
            ) {
                Text("Start Time")
            }

            Button(
                onClick = { showEndTimePicker = true }  // Changed to show custom picker
            ) {
                Text("End Time")
            }
        }
        // Add Time Picker Dialogs
        if (showStartTimePicker) {
            AdvancedTimePicker(
                onDismiss = { showStartTimePicker = false },
                onConfirm = { timePickerState ->
                    val calendar = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        set(Calendar.MINUTE, timePickerState.minute)
                    }
                    viewModel.onEvent(CreateTaskEvent.OnStartTimeChange(calendar.timeInMillis))
                    showStartTimePicker = false
                }
            )
        }

        if (showEndTimePicker) {
            AdvancedTimePicker(
                onDismiss = { showEndTimePicker = false },
                onConfirm = { timePickerState ->
                    val calendar = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        set(Calendar.MINUTE, timePickerState.minute)
                    }
                    viewModel.onEvent(CreateTaskEvent.OnEndTimeChange(calendar.timeInMillis))
                    showEndTimePicker = false
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Priority Selection
        PrioritySelector(
            selectedPriority = state.priority,
            onPrioritySelected = { priority ->
                viewModel.onEvent(CreateTaskEvent.OnPriorityChange(priority))
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.onEvent(CreateTaskEvent.OnCreateTask) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Task")
        }
    }

    if (state.isLoading) {
        CircularProgressIndicator()
    }

    state.error?.let { error ->
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun PrioritySelector(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column {
        Text("Priority", style = MaterialTheme.typography.labelLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Priority.values().forEach { priority ->
                FilterChip(
                    selected = priority == selectedPriority,
                    onClick = { onPrioritySelected(priority) },
                    label = { Text(priority.name) }
                )
            }
        }
    }
}





fun showDatePicker(
    context: Context,
    onDateSelected: (Long) -> Unit
) {
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            onDateSelected(calendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

