package com.example.todocompose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todocompose.addedit.TaskAddEditScreen
import com.example.todocompose.taskdetail.TaskDetailScreen
import com.example.todocompose.taskslist.TasksListScreen

@Composable
fun TodoNavGraph(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TASKS_LIST
    ){
        composable(TASKS_LIST){
            TasksListScreen(
                onClick = {navController.navigate(TASK_DETAIL)}
            )
        }

        composable(TASK_DETAIL){
            TaskDetailScreen(
                onClick = {navController.navigate(ADD_EDIT)}
            )
        }

        composable(ADD_EDIT){
            TaskAddEditScreen(
                onClick = {navController.navigate(TASKS_LIST)}
            )
        }
    }
}

const val TASKS_LIST = "tasksList"
const val TASK_DETAIL = "taskDetail"
const val ADD_EDIT = "addEdit"