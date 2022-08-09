package com.example.todomvvm.di

import android.content.Context
import androidx.room.Room
import com.example.todomvvm.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TaskModule {

    @Singleton
    @Provides
    fun provideTaskDatabase(@ApplicationContext context:Context) =
        Room.databaseBuilder(context, TaskDatabase::class.java, "task_database")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideTaskDao(db: TaskDatabase) = db.taskDao()
}