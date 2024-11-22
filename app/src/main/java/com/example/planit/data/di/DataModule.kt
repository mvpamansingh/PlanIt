package com.example.planit.data.di
import android.content.Context
import androidx.room.Room
import com.example.planit.data.local.TaskDatabase
import com.example.planit.data.local.dao.TaskDao
import com.example.planit.domain.repository.RoomRepository
import com.example.planit.domain.repository.RoomRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


fun provideRoomDatabase( context: Context):TaskDatabase
{
    return  Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        "task_database"
    ).build()
}

fun provideTaskDao(database: TaskDatabase):TaskDao
{
    return database.taskDao()
}

private fun provideRoomRepository(taskDao: TaskDao): RoomRepository {
    return RoomRepositoryImpl(taskDao)
}

val dataModule = module {
    // Database
    single {
        provideRoomDatabase(androidContext())
    }

    // DAO
    single {
        provideTaskDao(get())
    }


    single<RoomRepository>{
        provideRoomRepository(get())
    }
}