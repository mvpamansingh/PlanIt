package com.example.planit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.planit.data.local.dao.Converters
import com.example.planit.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}