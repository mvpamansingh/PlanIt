package com.example.planit.domain.repository

import com.example.planit.data.local.dao.TaskDao
import com.example.planit.data.local.entity.Priority
import com.example.planit.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow


class RoomRepositoryImpl (
    private val taskDao: TaskDao
):RoomRepository
{
    override suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

    override fun getTodayTasksByPriority(startOfDay: Long, endOfDay: Long): Flow<List<TaskEntity>> {
        return taskDao.getTodayTasksByPriority(startOfDay, endOfDay)
    }

    override fun getTasksForDateByTime(startOfDay: Long, endOfDay: Long): Flow<List<TaskEntity>> {
        return taskDao.getTasksForDateByTime(startOfDay = startOfDay,endOfDay=endOfDay)
    }

    override fun getAllTasksByPriority(): Flow<List<TaskEntity>> {

        return taskDao.getAllTasksByPriority()
    }

    override fun getTasksByPriority(priority: Priority): Flow<List<TaskEntity>> {

        return taskDao.getTasksByPriority(priority)
    }

    override suspend fun getTaskById(taskId: Long): TaskEntity? {
        return taskDao.getTaskById(taskId)
    }

}