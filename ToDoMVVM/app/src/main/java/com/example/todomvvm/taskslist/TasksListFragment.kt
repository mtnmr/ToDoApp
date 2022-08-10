package com.example.todomvvm.taskslist

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.todomvvm.R
import com.example.todomvvm.data.Task
import com.example.todomvvm.databinding.FragmentTasksListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksListFragment : Fragment() {

    private lateinit var binding : FragmentTasksListBinding

    private val tasksViewModel: TasksViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TasksListAdapter(tasksViewModel){
            val action = TasksListFragmentDirections.actionTasksListFragmentToTaskDetailFragment(it.id)
            view.findNavController().navigate(action)
        }
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            tasksRecyclerview.adapter = adapter
            listFragment = this@TasksListFragment
            viewModel = tasksViewModel
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.taks_list_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.menu_filter -> {
//                        Toast.makeText(requireContext(), "menu item selected", Toast.LENGTH_SHORT).show()
                        showFilterPopUpMenu()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    fun nextFragment(){
        val action = TasksListFragmentDirections.actionTasksListFragmentToTaskAddEditFragment()
        findNavController().navigate(action)
    }

    fun showFilterPopUpMenu(){
        val view = activity?.findViewById<View>(R.id.menu_filter)
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.task_filter_menu, menu)

            setOnMenuItemClickListener {
                tasksViewModel.taskFilterChange(
                    when(it.itemId){
                        R.id.completed_task -> TaskFilter.COMPLETED_TASK
                        R.id.active_task -> TaskFilter.ACTIVE_TASK
                        else -> TaskFilter.ALL_TASK
                    }
                )
                true
            }

            show()
        }
    }

}