package com.example.planit.data.local.dao

import androidx.room.TypeConverter
import com.example.planit.data.local.entity.Priority
import androidx.room.*

import com.example.planit.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TaskDao {
    // Insert task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    // Update task
    @Update
    suspend fun updateTask(task: TaskEntity)

    // Delete specific task
    @Delete
    suspend fun deleteTask(task: TaskEntity)

    // Get all tasks for today sorted by priority
    @Query("""
        SELECT * FROM tasks 
        WHERE date >= :startOfDay AND date < :endOfDay 
        ORDER BY 
            CASE priority 
                WHEN 'HIGH' THEN 1 
                WHEN 'MEDIUM' THEN 2 
                WHEN 'LOW' THEN 3 
            END
    """)
    fun getTodayTasksByPriority(
        startOfDay: Long = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis,
        endOfDay: Long = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis
    ): Flow<List<TaskEntity>>

    // Get tasks for specific date sorted by time in descending order
    @Query("""
        SELECT * FROM tasks 
        WHERE date >= :startOfDay AND date < :endOfDay 
        ORDER BY startTime DESC
    """)
    fun getTasksForDateByTime(startOfDay: Long, endOfDay: Long): Flow<List<TaskEntity>>

    // Get all tasks sorted by priority
    @Query("""
        SELECT * FROM tasks 
        ORDER BY 
            CASE priority 
                WHEN 'HIGH' THEN 1 
                WHEN 'MEDIUM' THEN 2 
                WHEN 'LOW' THEN 3 
            END
    """)
    fun getAllTasksByPriority(): Flow<List<TaskEntity>>

    // Get tasks by priority
    @Query("SELECT * FROM tasks WHERE priority = :priority")
    fun getTasksByPriority(priority: Priority): Flow<List<TaskEntity>>

    // Get task by id
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): TaskEntity?
}


class Converters {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}