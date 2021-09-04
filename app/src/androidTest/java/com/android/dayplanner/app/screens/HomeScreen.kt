package com.android.dayplanner.app.screens

import android.view.View
import com.agoda.kakao.check.KCheckBox
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.android.dayplanner.app.R
import com.android.dayplanner.app.TaskPlannerTests
import org.hamcrest.Matcher


class HomeScreen : Screen<HomeScreen>() {

    private val floatingActionButton = KButton { withId(R.id.floating_action_button) }

    private val recyclerView: KRecyclerView = KRecyclerView({
        withId(R.id.recyclerView)
    }, itemTypeBuilder = {
        itemType(::TaskItem)
    })

    class TaskItem(parent: Matcher<View>) : KRecyclerItem<TaskItem>(parent) {
        val title = KTextView(parent) { withId(R.id.textView_title) }
        val description = KTextView(parent) { withId(R.id.textView_description) }
        val date = KTextView(parent) { withId(R.id.textView_date) }
        val deleteTaskButton = KImageView(parent) { withId(R.id.imageView_delete) }
        val completeTaskButton = KCheckBox(parent) { withId(R.id.checkBox) }
    }

    fun assertRecyclerView(text: String) {
        recyclerView {
            firstChild<TaskItem> {
                title.hasText(text)
            }
        }
    }

    fun performClickOnFAButton() {
        recyclerView.isVisible()
        floatingActionButton.isVisible()

        floatingActionButton.click()
    }

    fun deleteTaskFromList() {
        recyclerView {
            firstChild<TaskItem> {
                title.isVisible()
                description.isVisible()
                date.isVisible()
                completeTaskButton.isVisible()

                deleteTaskButton.click()
            }
        }
    }

    fun clickOnTask() {
        recyclerView {
            firstChild<TaskItem> {
                title.isVisible()
                description.isVisible()
                date.isVisible()
                completeTaskButton.isVisible()
                deleteTaskButton.isVisible()

                click()
            }
        }
    }

    fun tasksListIsEmpty(): Boolean {
        return recyclerView.getSize() == 0
    }
}