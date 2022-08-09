package com.example.todomvvm.addedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todomvvm.databinding.FragmentTaskAddEditBinding
import com.example.todomvvm.taskdetail.TaskDetailFragmentDirections

class TaskAddEditFragment : Fragment() {

    private lateinit var binding:FragmentTaskAddEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            addEditFragment = this@TaskAddEditFragment
        }
    }

    fun nextFragment(){
        val action = TaskAddEditFragmentDirections.actionTaskAddEditFragmentToTasksListFragment()
        findNavController().navigate(action)
    }

}