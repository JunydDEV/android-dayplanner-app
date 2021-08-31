package com.android.dayplanner.app.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.dayplanner.app.data.TaskDatabase

class HomeViewModelFactory(private val taskDatabase: TaskDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(taskDatabase) as T
    }
}