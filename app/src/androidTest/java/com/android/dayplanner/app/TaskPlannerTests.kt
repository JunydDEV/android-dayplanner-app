package com.android.dayplanner.app

import androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu
import com.android.dayplanner.app.screens.HomeScreen
import com.android.dayplanner.app.screens.NewTaskScreen
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Test

class TaskPlannerTests : BaseUIClass() {

    @Test
    fun insertNewTaskHappyPath() {
        onScreen<HomeScreen> {
            performClickOnFAButton()
        }
        onScreen<NewTaskScreen> {
            saveHappyTask()
        }
        onScreen<HomeScreen> {
            assertRecyclerView(newTaskTitle)
        }
    }

    @Test
    fun insertNewTaskUnHappyPath() {
        onScreen<HomeScreen> {
            performClickOnFAButton()
        }
        onScreen<NewTaskScreen> {
            saveUnhappyTask()
        }
    }

    @Test
    fun deleteTask() {
        onScreen<HomeScreen> {
            deleteTaskFromList()
        }
    }

    @Test
    fun updateTaskHappyPath() {
        onScreen<HomeScreen> {
            if (tasksListIsEmpty()) {
                createNewTask()
            }
            clickOnTask()
            onScreen<NewTaskScreen> {
                updateTaskWithValidDetails()
            }
            onScreen<HomeScreen> {
                assertRecyclerView(updateTaskTitle)
            }
        }
    }

    @Test
    fun updateTaskUnHappyPath() {
        onScreen<HomeScreen> {
            if (tasksListIsEmpty()) {
                performClickOnFAButton()
                onScreen<NewTaskScreen> {
                    saveHappyTask()
                }
                onScreen<HomeScreen> {
                    assertRecyclerView(newTaskTitle)
                }
            }
            clickOnTask()
            onScreen<NewTaskScreen> {
                updateTaskWithInvalidDetails()
            }
        }
    }

    @Test
    fun testToolbar(){
        onScreen<HomeScreen> {
            assertToolbar()
            if(tasksListIsEmpty()) {
                createNewTask()
            }
        }

        openContextualActionModeOverflowMenu()

        onScreen<HomeScreen> {
            deleteAllTasks()
        }
    }

    private fun HomeScreen.createNewTask() {
        performClickOnFAButton()
        onScreen<NewTaskScreen> {
            saveHappyTask()
        }
        onScreen<HomeScreen> {
            assertRecyclerView(newTaskTitle)
        }
    }

    companion object {
        const val newTaskTitle = "New Task Title"
        const val updateTaskTitle = "Update Task Title"
    }

}

