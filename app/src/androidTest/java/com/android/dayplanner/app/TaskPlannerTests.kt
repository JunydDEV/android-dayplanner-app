package com.android.dayplanner.app

import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.android.dayplanner.app.screens.HomeScreen
import com.android.dayplanner.app.screens.NewTaskScreen
import org.junit.Test

class TaskPlannerTests: BaseUIClass() {

    @Test
    fun insertNewTaskHappyPath(){
        onScreen<HomeScreen> {
            performClickOnFAButton()
        }

        onScreen<NewTaskScreen> {
            saveHappyTask()
        }
    }

    @Test
    fun insertNewTaskUnHappyPath(){
        onScreen<HomeScreen> {
            performClickOnFAButton()
        }

        onScreen<NewTaskScreen> {
            saveUnhappyTask()
        }
    }

    @Test
    fun deleteTask(){
        onScreen<HomeScreen> {
            deleteTaskFromList()
        }
    }

    @Test
    fun updateTask(){
        onScreen<HomeScreen> {
            performClickOnFAButton()
        }

        onScreen<NewTaskScreen> {
            updateTaskDetails()
        }
    }



}