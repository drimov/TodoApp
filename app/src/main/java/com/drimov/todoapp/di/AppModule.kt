package com.drimov.todoapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.drimov.todoapp.data.TodoDatabase
import com.drimov.todoapp.data.repository.TodoRepositoryImpl
import com.drimov.todoapp.domain.repository.TodoRepository
import com.drimov.todoapp.util.ResourcesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideResourcesHelper(@ApplicationContext appContext: Context): ResourcesHelper {
        return ResourcesHelper(appContext)
    }
}