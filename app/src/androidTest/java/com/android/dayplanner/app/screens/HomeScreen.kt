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
import org.hamcrest.Matcher


class HomeScreen: Screen<HomeScreen>() {

    private val floatingActionButton = KButton { withId(R.id.floating_action_button) }

    private val recyclerView: KRecyclerView = KRecyclerView({
        withId(R.id.recyclerView)
    }, itemTypeBuilder = {
        itemType(::TaskItem)
    })

    class TaskItem(parent: Matcher<View>) : KRecyclerItem<TaskItem>(parent) {
        val title = KTextView(parent) { withId(R.id.textView_title) }
        val description = KTextView(parent) { withId(R.id.textView_description) }
        val deleteTaskButton = KImageView(parent) {withId(R.id.imageView_delete)}
        val completeTaskButton = KCheckBox(parent) {withId(R.id.checkBox)}
    }

    fun performClickOnFAButton(){
        floatingActionButton.click()
    }

    fun deleteTaskFromList() {
        recyclerView {
            firstChild<TaskItem> {
                deleteTaskButton.click()
            }
        }

    }

}