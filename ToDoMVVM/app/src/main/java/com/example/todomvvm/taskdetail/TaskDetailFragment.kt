package com.example.todomvvm.taskdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todomvvm.R
import com.example.todomvvm.data.Task
import com.example.todomvvm.databinding.FragmentTaskDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {

    private lateinit var binding:FragmentTaskDetailBinding

    private val taskDetailViewModel:TaskDetailViewModel by activityViewModels()

    private val args : TaskDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskId:Int = args.taskId
        taskDetailViewModel.updateTaskId(taskId)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            detailFragment = this@TaskDetailFragment
            viewmodel = taskDetailViewModel
        }
    }

    fun nextFragment(){
        val action = TaskDetailFragmentDirections.actionTaskDetailFragmentToTaskAddEditFragment(args.taskId)
        findNavController().navigate(action)
    }
}