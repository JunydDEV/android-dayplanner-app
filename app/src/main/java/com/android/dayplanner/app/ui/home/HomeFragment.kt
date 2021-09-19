package com.android.dayplanner.app.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dayplanner.app.R
import com.android.dayplanner.app.addAll
import com.android.dayplanner.app.data.Task
import com.android.dayplanner.app.databinding.HomeFragmentBinding
import com.android.dayplanner.app.ui.MainActivity
import com.pedrogomez.renderers.RVRendererAdapter
import com.pedrogomez.renderers.RendererBuilder


class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var mainActivity: MainActivity

    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(mainActivity.getDatabaseInstance())
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        //viewModel invoking
        viewModel.loadPendingTasks()
        viewModel.tasksListLiveData.observe(viewLifecycleOwner) { list ->
            tasksList.addAll(trashExistingItems = true, list)
            tasksAdapter.notifyDataSetChanged()
            binding.placeholder.visibility = if (list.isNotEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        //binding invoking
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tasksAdapter
        }
        binding.toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_taskDetailsFragment)
        }

        return binding.root
    }


    private fun gotoTaskDetails(task: Task) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToTaskDetailsFragment(
                task
            )
        )
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.showHistory -> {
                findNavController().navigate(R.id.action_homeFragment_to_tasksFragment)
            }
            else -> {
                if (tasksList.isNotEmpty()) {
                    askForConfirmation {
                        viewModel.deleteAllTasks { message ->
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "No tasks available", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return true
    }

    private fun askForConfirmation(onConfirm: () -> Unit) {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.delete_all_title))
            .setMessage(getString(R.string.delete_all_description))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> onConfirm.invoke() }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

}

