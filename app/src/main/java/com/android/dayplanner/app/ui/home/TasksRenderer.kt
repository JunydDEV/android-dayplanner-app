package com.android.dayplanner.app.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.android.dayplanner.app.R
import com.android.dayplanner.app.data.Status
import com.android.dayplanner.app.data.Task
import com.pedrogomez.renderers.Renderer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TasksRenderer(private val onTaskClick: (View, Task) -> Unit) : Renderer<Task>() {

    lateinit var textViewTitle: TextView
    lateinit var textViewDescription: TextView
    lateinit var textViewDate: TextView
    lateinit var statusView: View
    lateinit var imageViewDelete: ImageView
    lateinit var checkBox: CheckBox

    override fun inflate(inflater: LayoutInflater?, parent: ViewGroup?): View {
        val view = inflater?.inflate(R.layout.item_task, parent, false)

        view?.let {
            textViewTitle = view.findViewById(R.id.textView_title)
            textViewDescription = view.findViewById(R.id.textView_description)
            textViewDate = view.findViewById(R.id.textView_date)
            statusView = view.findViewById(R.id.view_status)
            imageViewDelete = view.findViewById(R.id.imageView_delete)
            checkBox = view.findViewById(R.id.checkBox)
        }

        return view!!
    }

    @DelicateCoroutinesApi
    override fun render() {
        textViewTitle.text = content.title
        textViewDescription.text = content.description
        textViewDate.text = content.createDate
        checkBox.isChecked = content.status == Status.COMPLETED
    }

    override fun hookListeners(rootView: View?) {
        super.hookListeners(rootView)

        rootView?.setOnClickListener {
            onTaskClick.invoke(rootView, content)
        }

        imageViewDelete.setOnClickListener {
            onTaskClick.invoke(it, content)
        }

        checkBox.setOnCheckedChangeListener { button, flag ->
            if(button.isPressed) {
                content.status = if (flag) Status.COMPLETED else Status.PENDING
                onTaskClick.invoke(checkBox, content)
            }
        }
    }
}