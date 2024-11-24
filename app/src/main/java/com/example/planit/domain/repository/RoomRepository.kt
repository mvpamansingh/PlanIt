package com.example.planit.domain.repository

import com.example.planit.data.local.entity.Priority
import com.example.planit.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    suspend fun insertTask(task:TaskEntity)

    suspend fun updateTask(task: TaskEntity)

    suspend fun deleteTask(task: TaskEntity)




    fun getTodayTasksByPriority(startOfDay: Long, endOfDay:Long): Flow<List<TaskEntity>>

    fun getTasksForDateByTime(startOfDay: Long, endOfDay: Long): Flow<List<TaskEntity>>

    fun getAllTasksByPriority():Flow<List<TaskEntity>>

    fun getTasksByPriority(priority: Priority): Flow<List<TaskEntity>>

    suspend fun getTaskById(taskId: Long): TaskEntity?



    fun getTasksForDate(date: Long): Flow<List<TaskEntity>>
}