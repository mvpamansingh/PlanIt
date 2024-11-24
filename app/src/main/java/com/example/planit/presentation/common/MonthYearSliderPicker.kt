package com.example.planit.presentation.common
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.text.SimpleDateFormat
import java.util.*




@Composable
fun MonthYearPickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit,
    initialDate: Long
) {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = initialDate
    }
    var selectedYear by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    var selectedMonth by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Month & Year") },
        text = {
            Column {
                // Year Picker
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { selectedYear-- }) {
                        Icon(Icons.Default.ChevronLeft, "Previous Year")
                    }
                    Text(
                        text = selectedYear.toString(),
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = { selectedYear++ }) {
                        Icon(Icons.Default.ChevronRight, "Next Year")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Month Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.height(200.dp)
                ) {
                    items(12) { index ->
                        val month = Calendar.getInstance().apply {
                            set(Calendar.MONTH, index)
                        }
                        val monthName = SimpleDateFormat("MMM", Locale.getDefault())
                            .format(month.time)

                        Surface(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            color = if (selectedMonth == index)
                                MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp),
                            onClick = { selectedMonth = index }
                        ) {
                            Text(
                                text = monthName,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = if (selectedMonth == index)
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    calendar.set(Calendar.YEAR, selectedYear)
                    calendar.set(Calendar.MONTH, selectedMonth)
                    onConfirm(calendar.timeInMillis)
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}