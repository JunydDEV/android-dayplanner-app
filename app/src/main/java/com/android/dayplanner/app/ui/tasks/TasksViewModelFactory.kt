package com.android.dayplanner.app.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.dayplanner.app.data.TaskDatabase

class TasksViewModelFactory(private val taskDatabase: TaskDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TasksViewModel(taskDatabase) as T
    }
}