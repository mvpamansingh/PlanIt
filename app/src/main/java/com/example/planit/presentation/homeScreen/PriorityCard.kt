package com.example.planit.presentation.homeScreen


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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.planit.presentation.scheduleScreen.TaskCard
import kotlinx.coroutines.launch





@Composable
private fun PriorityCard(
    modifier: Modifier = Modifier,
    title: String,
    taskCount: Int,
    progress: Int,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "$taskCount task",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            LinearProgressIndicator(
                progress = progress / 100f,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}



@Composable
fun PriorityCards(
    highPriorityTasks: Int,
    mediumPriorityTasks: Int,
    lowPriorityTasks: Int,
    highPriorityProgress: Int,
    mediumPriorityProgress: Int,
    lowPriorityProgress: Int,
    onHighPriorityClick: () -> Unit,
    onMediumPriorityClick: () -> Unit,
    onLowPriorityClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Large card on the left
        PriorityCard(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            title = "First Priority",
            taskCount = highPriorityTasks,
            progress = highPriorityProgress,
            backgroundColor = MaterialTheme.colorScheme.errorContainer,
            onClick = onHighPriorityClick
        )

        // Column with two smaller cards on the right
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PriorityCard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                title = "Second Priority",
                taskCount = mediumPriorityTasks,
                progress = mediumPriorityProgress,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                onClick = onMediumPriorityClick
            )

            PriorityCard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                title = "Third Priority",
                taskCount = lowPriorityTasks,
                progress = lowPriorityProgress,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                onClick = onLowPriorityClick
            )
        }
    }
}