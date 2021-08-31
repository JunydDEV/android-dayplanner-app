package com.android.dayplanner.app.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.dayplanner.app.addAll
import com.android.dayplanner.app.data.Task
import com.android.dayplanner.app.data.TaskDatabase
import com.android.dayplanner.app.data.TasksRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val taskDatabase: TaskDatabase) : ViewModel() {

    private val tasksList = mutableListOf<Task>()
    val tasksListLiveData = MutableLiveData<List<Task>>().apply {
        value = tasksList
    }

    private val tasksRepository by lazy { TasksRepository(taskDatabase) }

    fun loadPendingTasks() {
        viewModelScope.launch {
            tasksList.addAll(true, tasksRepository.getPendingTasks()).also {
                tasksListLiveData.value = tasksList
            }
        }
    }

    fun deleteTask(task: Task, onComplete: (String) -> Unit) {
        viewModelScope.launch {
            tasksRepository.removeTask(task){
                tasksList.remove(task).also {
                    tasksListLiveData.value = tasksList
                }
                onComplete.invoke(it)
            }
        }
    }

    fun completeTask(task: Task, onComplete: (String) -> Unit) {
        viewModelScope.launch {
            tasksRepository.changeTaskStatus(task) { message ->
                tasksList.removeAll { it.id == task.id }.also {
                    tasksListLiveData.value =tasksList
                }
                onComplete.invoke(message)
            }
        }
    }

}