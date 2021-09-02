package com.android.dayplanner.app.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.android.dayplanner.app.R
import com.android.dayplanner.app.data.Task
import com.android.dayplanner.app.ui.MainActivity
import java.util.*

class TaskDetailsFragment : Fragment(R.layout.task_details_fragment) {

    private lateinit var mainActivity: MainActivity
    private val viewModel by viewModels<TaskDetailsViewModel> {
        TaskViewModelFactory(mainActivity.getDatabaseInstance())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Task>("keyTask").run {
            this?.let {
                view.findViewById<EditText>(R.id.editText_title).run {
                    setText(title)
                }
                view.findViewById<EditText>(R.id.editText_description).run {
                    setText(description)
                }
                view.findViewById<EditText>(R.id.editText_taskDate).run {
                    setText(createDate)
                }
                view.findViewById<Button>(R.id.button_saveTask).run {
                    text = context.getString(R.string.edit)
                }

                view.findViewById<Toolbar>(R.id.toolbar).run {
                    title = "Edit Task"
                }

            }
        }

        view.findViewById<Button>(R.id.button_saveTask).run {
            setOnClickListener {
                if (arguments != null) {
                    viewModel.updateTask(getUpdateTask()!!) {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                } else {
                    viewModel.saveTask(createTask()) {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }

        view.findViewById<Button>(R.id.button_createLongTempTask).run {
            setOnClickListener {
                view.findViewById<EditText>(R.id.editText_title).run {
                    setText(context.getString(R.string.dummy_brief_task_title))
                }
                view.findViewById<EditText>(R.id.editText_description).run {
                    setText(context.getString(R.string.dummy_brief_task_description))
                }
                view.findViewById<EditText>(R.id.editText_taskDate).run {
                    setText(Calendar.getInstance().time.toString())
                }
            }
        }

        view.findViewById<Button>(R.id.button_createShortTempTask).run {
            setOnClickListener {
                view.findViewById<EditText>(R.id.editText_title).run {
                    setText(context.getString(R.string.dummy_short_task_title))
                }
                view.findViewById<EditText>(R.id.editText_description).run {
                    setText(context.getString(R.string.dummy_short_task_description))
                }
                view.findViewById<EditText>(R.id.editText_taskDate).run {
                    setText(Calendar.getInstance().time.toString())
                }
            }
        }

    }

    private fun createTask(): Task {
        return Task(
            title = getValueFromField(R.id.editText_title),
            description = getValueFromField(R.id.editText_description),
            createDate = getValueFromField(R.id.editText_taskDate)
        )
    }

    private fun getUpdateTask(): Task? {
        arguments?.getParcelable<Task>("keyTask")?.run {
            title = getValueFromField(R.id.editText_title)
            description = getValueFromField(R.id.editText_description)
            createDate = getValueFromField(R.id.editText_taskDate)
            return this
        }
        return null
    }

    private fun getValueFromField(id: Int): String {
        view?.findViewById<EditText>(id)?.run {
            return text.toString()
        }
        return ""
    }
}