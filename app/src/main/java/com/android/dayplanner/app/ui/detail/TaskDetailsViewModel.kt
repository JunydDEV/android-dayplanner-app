package com.android.dayplanner.app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.dayplanner.app.data.Task
import com.android.dayplanner.app.data.TaskDatabase
import com.android.dayplanner.app.data.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDetailsViewModel(private val taskDatabase: TaskDatabase) : ViewModel() {

    private val tasksRepository by lazy { TasksRepository(taskDatabase) }

    fun saveTask(task: Task, onComplete: (String) -> Unit) {
        viewModelScope.launch {
            tasksRepository.addTask(task){
                onComplete.invoke(it)
            }
        }
    }

    fun updateTask(task: Task, onComplete: (String) -> Unit) {
        viewModelScope.launch {
            tasksRepository.updateTask(task) {
                onComplete.invoke(it)
            }
        }
    }

}