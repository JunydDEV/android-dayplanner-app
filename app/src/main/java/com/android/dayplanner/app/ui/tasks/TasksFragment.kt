package com.android.dayplanner.app.ui.tasks

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.dayplanner.app.R
import com.android.dayplanner.app.addAll
import com.android.dayplanner.app.data.Task
import com.android.dayplanner.app.ui.MainActivity
import com.android.dayplanner.app.ui.detail.TaskDetailsViewModel
import com.android.dayplanner.app.ui.detail.TaskViewModelFactory
import com.android.dayplanner.app.ui.home.HomeFragmentDirections
import com.android.dayplanner.app.ui.home.TasksRenderer
import com.pedrogomez.renderers.RVRendererAdapter
import com.pedrogomez.renderers.RendererBuilder

class TasksFragment : Fragment(R.layout.tasks_fragment) {

    private lateinit var mainActivity: MainActivity
    private val viewModel by viewModels<TasksViewModel> {
        TasksViewModelFactory(mainActivity.getDatabaseInstance())
    }
    private val tasksList by lazy { mutableListOf<Task>() }
    private val rendererBuilder by lazy {
        RendererBuilder(TasksRenderer { view, task ->
            registerTaskViewsClickListeners(view, task)
        })
    }
    private val tasksAdapter by lazy { RVRendererAdapter(rendererBuilder, tasksList) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recyclerView).run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tasksAdapter
        }

        viewModel.loadTasks()

        viewModel.tasksListLiveData.observe(viewLifecycleOwner, { list ->
            tasksList.addAll(trashExistingItems = true, list)
            tasksAdapter.notifyDataSetChanged()
        })

    }

    private fun registerTaskViewsClickListeners(view: View, task: Task) {
        when (view.id) {
            R.id.imageView_delete -> {
                viewModel.deleteTask(task) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
            R.id.checkBox -> {
                viewModel.completeTask(task) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                gotoTaskDetails(task)
            }
        }
    }

    private fun gotoTaskDetails(task: Task) {
        findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToTaskDetailsFragment(task))
    }

}