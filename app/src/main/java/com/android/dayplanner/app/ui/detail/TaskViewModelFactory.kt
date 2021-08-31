package com.android.dayplanner.app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.dayplanner.app.data.TaskDatabase

class TaskViewModelFactory(private val taskDatabase: TaskDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskDetailsViewModel(taskDatabase) as T
    }
}