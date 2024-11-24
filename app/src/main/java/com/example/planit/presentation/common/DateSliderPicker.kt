package com.example.planit.presentation.common



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*



@Composable
fun DateSliderPicker(
    onDateSelected: (Long) -> Unit,
    selectedDate: Long = System.currentTimeMillis()
) {
    val calendar = Calendar.getInstance()
    val dates = remember(selectedDate) {
        generateDateList(selectedDate) // Pass selectedDate instead of currentDate
    }
    val listState = rememberLazyListState()
    val dateFormatter = SimpleDateFormat("dd", Locale.getDefault())
    val dayFormatter = SimpleDateFormat("EEE", Locale.getDefault())
    val monthFormatter = SimpleDateFormat("MMMM, yyyy", Locale.getDefault())
    var showMonthYearPicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Month Year Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clickable { showMonthYearPicker = true }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Select month and year",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = monthFormatter.format(Date(selectedDate)),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(dates) { date ->
                DateItem(
                    date = date,
                    isSelected = isSameDay(date, selectedDate),
                    dateFormatter = dateFormatter,
                    dayFormatter = dayFormatter,
                    onClick = { onDateSelected(date) }
                )
            }
        }
    }

    if (showMonthYearPicker) {
        MonthYearPickerDialog(
            onDismiss = { showMonthYearPicker = false },
            onConfirm = { newDate ->
                onDateSelected(newDate)
                showMonthYearPicker = false
            },
            initialDate = selectedDate
        )
    }
}

private fun generateDateList(baseDate: Long): List<Long> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = baseDate
    // Keep the selected month and year, just move to -15 days from the current day
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.add(Calendar.DAY_OF_MONTH, -15)

    val dates = mutableListOf<Long>()
    repeat(31) {
        dates.add(calendar.timeInMillis)
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }
    return dates
}
@Composable
private fun DateItem(
    date: Long,
    isSelected: Boolean,
    dateFormatter: SimpleDateFormat,
    dayFormatter: SimpleDateFormat,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(45.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = dayFormatter.format(Date(date)).uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                color = if (isSelected)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = dateFormatter.format(Date(date)),
            style = if (isSelected)
                MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            else
                MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
        )
    }
}



 fun isSameDay(date1: Long, date2: Long): Boolean {
    val cal1 = Calendar.getInstance().apply { timeInMillis = date1 }
    val cal2 = Calendar.getInstance().apply { timeInMillis = date2 }
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
}
