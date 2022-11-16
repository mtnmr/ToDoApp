package com.example.todomvvm.di

import com.example.todomvvm.data.ITaskRepository
import com.example.todomvvm.data.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsRepository(repository: TaskRepository):ITaskRepository
}