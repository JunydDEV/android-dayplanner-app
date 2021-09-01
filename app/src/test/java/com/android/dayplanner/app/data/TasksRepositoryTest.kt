package com.android.dayplanner.app.data

import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class TasksRepositoryTest{

    private lateinit var taskDatabase: TaskDatabase
    private lateinit var taskDao: TaskDao

    private lateinit var sut:TasksRepository

    @Before
    fun setup(){
        taskDatabase = Mockito.mock(TaskDatabase::class.java)
        taskDao = Mockito.mock(TaskDao::class.java)

        Mockito.`when`(taskDatabase.taskDao()).thenReturn(taskDao)

        sut = TasksRepository(taskDatabase)
    }

    @Test
    fun `addTask returns insertion success message when task is valid`() {
        val validTask =
            Task(id = "1234", "task title", "task description", status = Status.COMPLETED)

        sut.addTask(validTask) { message ->
            assertNotNull(message)
            assertEquals("Task inserted successfully", message)
        }
    }

    @Test
    fun `addTask returns validation error message when task is invalid`() {
        val validTask =
            Task(id = "1234", "", "task description", status = Status.COMPLETED)

        sut.addTask(validTask) { message ->
            assertNotNull(message)
            assertEquals("Title is empty", message)
        }
    }

    @Test
    fun `getPendingTasks returns empty list when list doesn't contain pending tasks`() {
        Mockito.`when`(taskDatabase.taskDao().getTasks()).thenReturn(emptyPendingTaskList())

        val list = sut.getPendingTasks()

        assertNotNull(list)
        assertTrue(list.isEmpty())
    }

    @Test
    fun `getPendingTasks returns non empty list when list contains pending tasks`() {
        Mockito.`when`(taskDatabase.taskDao().getTasks()).thenReturn(nonEmptyPendingTaskList())

        val list = sut.getPendingTasks()

        assertNotNull(list)
        assertTrue(list.isNotEmpty())
        assertTrue(list.size == 3)
    }

    private fun nonEmptyPendingTaskList(): List<Task>? {
        return mutableListOf<Task>().apply {
            add(Task(id = "1234", "task title", "task description", status = Status.PENDING))
            add(Task(id = "1235", "task title 2", "task description", status = Status.PENDING))
            add(Task(id = "1236", "task title 3", "task description", status = Status.COMPLETED))
            add(Task(id = "1237", "task title 4", "task description", status = Status.PENDING))
        }
    }

    private fun emptyPendingTaskList(): List<Task>? {
        return mutableListOf<Task>().apply {
            add(Task(id = "1234", "task title", "task description", status = Status.COMPLETED))
            add(Task(id = "1235", "task title 2", "task description", status = Status.COMPLETED))
            add(Task(id = "1236", "task title 3", "task description", status = Status.COMPLETED))
            add(Task(id = "1237", "task title 4", "task description", status = Status.COMPLETED))
        }
    }

}