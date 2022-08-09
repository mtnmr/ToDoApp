package com.example.todomvvm.taskslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.todomvvm.R
import com.example.todomvvm.data.Task
import com.example.todomvvm.databinding.FragmentTasksListBinding

class TasksListFragment : Fragment() {

    private lateinit var binding : FragmentTasksListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TasksListAdapter{
            val action = TasksListFragmentDirections.actionTasksListFragmentToTaskDetailFragment()
            view.findNavController().navigate(action)
        }
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            tasksRecyclerview.adapter = adapter
        }

        adapter.submitList(sampleList)
    }

}