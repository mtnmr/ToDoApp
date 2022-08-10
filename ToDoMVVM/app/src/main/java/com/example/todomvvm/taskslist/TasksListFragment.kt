package com.example.todomvvm.taskslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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

    }

    fun nextFragment(){
        val action = TasksListFragmentDirections.actionTasksListFragmentToTaskAddEditFragment()
        findNavController().navigate(action)
    }

}