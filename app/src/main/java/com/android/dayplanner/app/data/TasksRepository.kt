package com.android.dayplanner.app.data

class TasksRepository(var db: TaskDatabase) {

    fun addTask(task: Task, onComplete: (String) -> Unit) {
        task.performValidation { isValid, message ->
            if (isValid) {
                db.taskDao().insertTask(task)
                onComplete.invoke("Task inserted successfully")
            } else {
                onComplete.invoke(message)
            }
        }
    }

    fun getAllTasks(): List<Task> {
        return db.taskDao().getTasks().reversed()
    }

    fun getPendingTasks(): List<Task> {
        return db.taskDao().getTasks().filter { it.status == Status.PENDING }.reversed()
    }

    fun removeTask(task: Task, onComplete: (String) -> Unit) {
        db.taskDao().deleteTask(task)
        onComplete.invoke("Task removed")
    }

    fun updateTask(task: Task, onComplete: (String) -> Unit) {
        task.performValidation { isValid, message ->
            if (isValid) {
                db.taskDao().updateTask(task)
                onComplete.invoke("Task updated successfully")
            } else {
                onComplete.invoke(message)
            }
        }
    }

    fun changeTaskStatus(task: Task, onComplete: (String) -> Unit) {
        db.taskDao().updateTask(task)
        val message = if (task.status == Status.PENDING) {
            "Task is pending"
        } else {
            "Task completed"
        }
        onComplete.invoke(message)
    }

    fun deletePendingTasks(onDelete: (String) -> Unit) {
        val deletedTasksCount = db.taskDao().deleteTaskByStatus(Status.PENDING)
        if ( deletedTasksCount > 0){
            onDelete.invoke("$deletedTasksCount task(s) deleted.")
        } else {
            onDelete.invoke("there are no tasks to be deleted.")
        }
    }
}