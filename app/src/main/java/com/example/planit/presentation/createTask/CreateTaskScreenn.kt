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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.planit.presentation.common.AdvancedTimePicker
import com.example.planit.presentation.common.CustomDatePickerDialog
import com.example.planit.presentation.common.DateSliderPicker
import java.text.SimpleDateFormat
import java.util.*

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CreateTaskScreenn(
//    viewModel: CreateTaskViewModel = koinViewModel(),
//    onNavigateBack: () -> Unit
//) {
//    val state = viewModel.state.value
//    val context = LocalContext.current
//    var showStartTimePicker by remember { mutableStateOf(false) }
//    var showDatePicker by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        // Purple Card
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = MaterialTheme.colorScheme.primary
//            ),
//            shape = RoundedCornerShape(16.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text(
//                    text = "Task title",
//                    style = MaterialTheme.typography.labelMedium,
//                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
//                )
//
//                OutlinedTextField(
//                    value = state.title,
//                    onValueChange = { viewModel.onEvent(CreateTaskEvent.OnTitleChange(it)) },
//                    colors = OutlinedTextFieldDefaults.colors(
//                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
//                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
//                        unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
//                        focusedTextColor = MaterialTheme.colorScheme.onPrimary
//                    ),
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(
//                    text = "Due date",
//                    style = MaterialTheme.typography.labelMedium,
//                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
//                )
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Schedule,
//                        contentDescription = "Time",
//                        tint = MaterialTheme.colorScheme.onPrimary
//                    )
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable { showDatePicker = true },
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
//                                .format(Date(state.date)),
//                            color = MaterialTheme.colorScheme.onPrimary
//                        )
//                        Icon(
//                            imageVector = Icons.Default.KeyboardArrowRight,
//                            contentDescription = "Select date",
//                            tint = MaterialTheme.colorScheme.onPrimary
//                        )
//                    }
//                }
//            }
//        }
//
//        // Description Section
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Descriptions",
//                style = MaterialTheme.typography.titleMedium,
//                color = MaterialTheme.colorScheme.onBackground
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            OutlinedTextField(
//                value = state.description,
//                onValueChange = { viewModel.onEvent(CreateTaskEvent.OnDescriptionChange(it)) },
//                modifier = Modifier.fillMaxWidth(),
//                minLines = 3,
//                colors = OutlinedTextFieldDefaults.colors(
//                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
//                    focusedBorderColor = MaterialTheme.colorScheme.primary
//                )
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Priority Selection
//            PrioritySelector(
//                selectedPriority = state.priority,
//                onPrioritySelected = { priority ->
//                    viewModel.onEvent(CreateTaskEvent.OnPriorityChange(priority))
//                }
//            )
//        }
//
//        // Create Task Button at bottom
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            contentAlignment = Alignment.BottomCenter
//        ) {
//            Button(
//                onClick = { viewModel.onEvent(CreateTaskEvent.OnCreateTask) },
//                modifier = Modifier
//                    .fillMaxWidth(0.7f)
//                    .height(48.dp),
//                shape = RoundedCornerShape(24.dp)
//            ) {
//                Text("SAVE")
//            }
//        }
//    }
//
//    // Dialogs
//    if (showDatePicker) {
//        CustomDatePickerDialog(
//            onDismiss = { showDatePicker = false },
//            onConfirm = { date ->
//                viewModel.onEvent(CreateTaskEvent.OnDateChange(date))
//                showDatePicker = false
//            },
//            selectedDate = state.date
//        )
//    }
//
//    if (state.isLoading) {
//        CircularProgressIndicator()
//    }
//
//    state.error?.let { error ->
//        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
//    }
//}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreenn(
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
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Purple Card with TopAppBar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // TopAppBar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Text(
                        text = "Details project",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Task title",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                )

                OutlinedTextField(
                    value = state.title,
                    onValueChange = { viewModel.onEvent(CreateTaskEvent.OnTitleChange(it)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(16.dp))


                Text(text ="Due Date" ,color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 8.dp))

                // Time and Date Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Time Selection
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showStartTimePicker = true },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Time",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = SimpleDateFormat("hh:mm a", Locale.getDefault())
                                .format(Date(state.startTime)),
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Text(
                            text = " - ",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = SimpleDateFormat("hh:mm a", Locale.getDefault())
                                .format(Date(state.endTime)),
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.clickable { showEndTimePicker = true }
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Date Selection
                    Row(
                        modifier = Modifier
                            .clickable { showDatePicker = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Select date",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = SimpleDateFormat("MMM dd", Locale.getDefault())
                                .format(Date(state.date)),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }

        // Description Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Descriptions",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.description,
                onValueChange = { viewModel.onEvent(CreateTaskEvent.OnDescriptionChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Priority Selection
            PrioritySelector(
                selectedPriority = state.priority,
                onPrioritySelected = { priority ->
                    viewModel.onEvent(CreateTaskEvent.OnPriorityChange(priority))
                }
            )
        }

        // Create Task Button at bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { viewModel.onEvent(CreateTaskEvent.OnCreateTask) },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("SAVE")
            }
        }
    }

    // Time Picker Dialogs
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

    // Date Picker Dialog
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

    if (state.isLoading) {
        CircularProgressIndicator()
    }

    state.error?.let { error ->
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}