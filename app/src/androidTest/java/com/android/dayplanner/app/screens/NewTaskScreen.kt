package com.android.dayplanner.app.screens

import androidx.test.espresso.Espresso
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.android.dayplanner.app.R
import com.android.dayplanner.app.TaskPlannerTests

class NewTaskScreen: Screen<NewTaskScreen>() {

    private val buttonCreateTask = KButton{ withId(R.id.button_createLongTempTask) }
    private val buttonSaveTask = KButton{ withId(R.id.button_saveTask) }
    private val editTextTitle = KEditText{ withId(R.id.editText_title) }
    private val editTextDescription = KEditText{ withId(R.id.editText_description) }
    private val editTextDate = KEditText{ withId(R.id.editText_taskDate) }

    fun saveHappyTask(){
        buttonCreateTask.click()

        editTextTitle.replaceText(TaskPlannerTests.newTaskTitle)
        editTextTitle.hasAnyText()
        editTextDescription.hasAnyText()
        editTextDate.hasAnyText()

        buttonSaveTask.click()
    }

    fun saveUnhappyTask(){
        buttonCreateTask.click()

        editTextTitle.replaceText("")
        editTextDescription.hasAnyText()
        editTextDate.hasAnyText()

        Espresso.closeSoftKeyboard()

        buttonSaveTask.click()
    }

    fun updateTaskWithValidDetails() {
        editTextTitle.replaceText(TaskPlannerTests.updateTaskTitle)

        editTextTitle.hasAnyText()
        editTextDescription.hasAnyText()
        editTextDate.hasAnyText()

        Espresso.closeSoftKeyboard()

        buttonSaveTask.click()
    }


    fun updateTaskWithInvalidDetails() {
        editTextTitle.replaceText("")

        editTextDescription.hasAnyText()
        editTextDate.hasAnyText()

        Espresso.closeSoftKeyboard()

        buttonSaveTask.click()
    }
}