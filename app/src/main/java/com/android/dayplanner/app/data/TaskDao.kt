package com.android.dayplanner.app.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM Task")
    fun getTasks(): List<Task>

    @Update
    fun updateTask(task: Task)
}