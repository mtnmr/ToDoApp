package com.example.todomvvm.addedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todomvvm.data.Task
import com.example.todomvvm.databinding.FragmentTaskAddEditBinding
import com.example.todomvvm.taskdetail.TaskDetailFragmentDirections
import com.example.todomvvm.taskdetail.TaskDetailViewModel
import com.example.todomvvm.taskslist.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskAddEditFragment : Fragment() {

    private lateinit var binding:FragmentTaskAddEditBinding

    private val taskAddViewModel : TaskAddViewModel by activityViewModels()
    private val taskDetailViewModel: TaskDetailViewModel by activityViewModels()

    private val args:TaskAddEditFragmentArgs by navArgs()

    private lateinit var task:Task

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
            viewModel = taskAddViewModel
        }

        val taskId:Int = args.taskId
        if (taskId >= 0){
            task = taskDetailViewModel.currentTask.value!!
            taskAddViewModel.setEditTask(task)
        }else{
            taskAddViewModel.resetTaskText()
        }
    }


    fun addTask(){
        if (taskAddViewModel.taskTitle.value == ""){
            Toast.makeText(requireContext(), "タイトルを入力してください", Toast.LENGTH_SHORT).show()
            return
        }

        if (args.taskId >= 0) {
            taskAddViewModel.editTask(
                task.id,
                taskAddViewModel.taskTitle.value.toString(),
                taskAddViewModel.taskDescription.value.toString(),
                task.isChecked
            )
        }else{
            taskAddViewModel.addTask(
                taskAddViewModel.taskTitle.value.toString(),
                taskAddViewModel.taskDescription.value.toString()
            )
        }

        taskAddViewModel.resetTaskText()

        val action = TaskAddEditFragmentDirections.actionTaskAddEditFragmentToTasksListFragment()
        findNavController().navigate(action)
    }

}