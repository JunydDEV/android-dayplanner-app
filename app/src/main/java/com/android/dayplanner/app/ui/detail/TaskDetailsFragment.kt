package com.android.dayplanner.app.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.android.dayplanner.app.R
import com.android.dayplanner.app.data.Task
import com.android.dayplanner.app.databinding.TaskDetailsFragmentBinding
import com.android.dayplanner.app.ui.MainActivity
import java.util.*

class TaskDetailsFragment : Fragment(R.layout.task_details_fragment) {

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: TaskDetailsFragmentBinding

    private val viewModel by viewModels<TaskDetailsViewModel> {
        TaskViewModelFactory(mainActivity.getDatabaseInstance())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.task_details_fragment, container, false)

        arguments?.getParcelable<Task>("keyTask").apply {
            this?.let {
                binding.editTextTitle.apply {
                    setText(title)
                }
                binding.editTextDescription.apply {
                    setText(description)
                }
                binding.editTextTaskDate.apply {
                    setText(createDate)
                }
                binding.buttonSaveTask.apply {
                    text = context.getString(R.string.edit)
                }

                binding.toolbar.apply {
                    title = context.getString(R.string.button_edit_task)
                }
            }
        }

        binding.buttonSaveTask.apply {
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

        binding.buttonCreateLongTempTask.apply {
            setOnClickListener {
                binding.editTextTitle.apply {
                    setText(context.getString(R.string.dummy_brief_task_title))
                }
                binding.editTextDescription.apply {
                    setText(context.getString(R.string.dummy_brief_task_description))
                }
                binding.editTextTaskDate.apply {
                    setText(Calendar.getInstance().time.toString())
                }
            }
        }

        binding.buttonCreateShortTempTask.apply {
            setOnClickListener {
                binding.editTextTitle.apply {
                    setText(context.getString(R.string.dummy_short_task_title))
                }
                binding.editTextDescription.apply {
                    setText(context.getString(R.string.dummy_short_task_description))
                }
                binding.editTextTaskDate.apply {
                    setText(Calendar.getInstance().time.toString())
                }
            }
        }

        return binding.root
    }

    private fun createTask(): Task {
        return Task(
            title = getValueFromField(binding.editTextTitle),
            description = getValueFromField(binding.editTextDescription),
            createDate = getValueFromField(binding.editTextTaskDate)
        )
    }

    private fun getUpdateTask(): Task? {
        arguments?.getParcelable<Task>("keyTask")?.run {
            title = getValueFromField(binding.editTextTitle)
            description = getValueFromField(binding.editTextDescription)
            createDate = getValueFromField(binding.editTextTaskDate)
            return this
        }
        return null
    }

    private fun getValueFromField(view: TextView): String {
        view.apply {
            return text.toString()
        }
    }
}