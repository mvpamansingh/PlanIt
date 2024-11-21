package com.example.planit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class TaskEntity
    (
        @PrimaryKey(autoGenerate = true)
        val id:Long=0,

        @ColumnInfo(name = "description")
        val description:String,

        @ColumnInfo(name ="title")
        val title:String,

        @ColumnInfo(name ="startTime")
        val startTime:Long ,

        @ColumnInfo(name="endTime")
        val endTime:Long,

        @ColumnInfo(name = "date")
        val date: Long,

        @ColumnInfo(name = "priority")
        val priority: Priority,

        @ColumnInfo(name = "is_completed")
        val isCompleted: Boolean = false,

        @ColumnInfo(name = "created_at")
        val createdAt: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "updated_at")
        val updatedAt: Long = System.currentTimeMillis()

    )



enum class Priority{
    HIGH,
    MEDIUM,
    LOW
}