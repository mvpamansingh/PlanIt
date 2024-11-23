package com.example.planit.presentation.common
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DatePicker(
    onDateSelected: (Long) -> Unit,
    selectedDate: Long = System.currentTimeMillis()
) {
    var showYearPicker by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance().apply { timeInMillis = selectedDate }
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    var currentSelectedDate by remember { mutableStateOf(selectedDate) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Month and Year Header with arrows
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                calendar.add(Calendar.MONTH, -1)
                currentSelectedDate = calendar.timeInMillis
                onDateSelected(calendar.timeInMillis)
            }) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Previous Month"
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { showYearPicker = true }
            ) {
                Text(
                    text = SimpleDateFormat("MMMM", Locale.getDefault())
                        .format(calendar.time),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = currentYear.toString(),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            IconButton(onClick = {
                calendar.add(Calendar.MONTH, 1)

                currentSelectedDate = calendar.timeInMillis
                onDateSelected(calendar.timeInMillis)
            }) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Next Month"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Days of week header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Calendar grid
        MonthCalendarGrid(
            selectedDate = currentSelectedDate,
            currentYear = currentYear,
            currentMonth = currentMonth,
            onDateSelected = { date ->
                currentSelectedDate = date
                onDateSelected(date)
            }
        )
    }

    // Year picker dialog
    if (showYearPicker) {
        YearMonthPickerDialog(
            initialYear = currentYear,
            initialMonth = currentMonth,
            onDismiss = { showYearPicker = false },
            onConfirm = { year, month ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                currentSelectedDate = calendar.timeInMillis
                onDateSelected(calendar.timeInMillis)
                showYearPicker = false
            }

        )
    }
}

@Composable
private fun MonthCalendarGrid(
    selectedDate: Long,
    currentYear: Int,
    currentMonth: Int,
    onDateSelected: (Long) -> Unit
) {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, currentYear)
        set(Calendar.MONTH, currentMonth)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val selectedCalendar = Calendar.getInstance().apply {
        timeInMillis = selectedDate
    }
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.height(240.dp)
    ) {
        // Empty spaces for first week
        items(firstDayOfWeek) {
            Box(modifier = Modifier.aspectRatio(1f))
        }

        // Days of month
//        items(daysInMonth) { day ->
//            DayCell(
//                day = day + 1,
//                isSelected = isSameDay(
//                    calendar.apply { set(Calendar.DAY_OF_MONTH, day + 1) }.timeInMillis,
//                    selectedDate
//                ),
//                onDateSelected = { selectedDay ->
//                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
//                    onDateSelected(calendar.timeInMillis)
//                }
//            )
//        }
        // Days of month
        items(daysInMonth) { day ->
            val dayCalendar = calendar.clone() as Calendar
            dayCalendar.set(Calendar.DAY_OF_MONTH, day + 1)

            DayCell(
                day = day + 1,
                isSelected = isSameDay(dayCalendar.timeInMillis, selectedDate),
                onDateSelected = { selectedDay ->
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
                    onDateSelected(calendar.timeInMillis)
                }
            )
        }
    }
}

@Composable
 fun YearMonthPickerDialog(
    initialYear: Int,
    initialMonth: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit
) {
    var selectedYear by remember { mutableStateOf(initialYear) }
    var selectedMonth by remember { mutableStateOf(initialMonth) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Year picker
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val years = (initialYear - 10..initialYear + 10).toList()
                    items(years) { year ->
                        YearChip(
                            year = year,
                            isSelected = year == selectedYear,
                            onClick = { selectedYear = year }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Month picker
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.height(200.dp)
                ) {
                    val months = (0..11).toList()
                    items(months) { month ->
                        MonthChip(
                            month = month,
                            isSelected = month == selectedMonth,
                            onClick = { selectedMonth = month }
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onConfirm(selectedYear, selectedMonth) }
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}


@Composable
fun DayCell(
    day: Int,
    isSelected: Boolean,
    onDateSelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        ),
        onClick = { onDateSelected(day) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun YearChip(
    year: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(year.toString()) },
        modifier = Modifier.padding(4.dp)
    )
}

@Composable
fun MonthChip(
    month: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val monthName = remember(month) {
        SimpleDateFormat("MMMM", Locale.getDefault()).format(
            Calendar.getInstance().apply { set(Calendar.MONTH, month) }.time
        )
    }

    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(monthName) },
        modifier = Modifier.padding(4.dp)
    )
}