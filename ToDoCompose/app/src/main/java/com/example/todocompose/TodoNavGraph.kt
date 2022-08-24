package com.example.todocompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todocompose.addedit.TaskAddEditScreen
import com.example.todocompose.taskdetail.TaskDetailScreen
import com.example.todocompose.taskslist.TasksListScreen

@Composable
fun TodoNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TASKS_LIST
    ) {
        composable(TASKS_LIST) {
            TasksListScreen(
                onClick = { navController.navigate(ADD_EDIT) },
                onItemClick = { id -> navController.navigate("$TASK_DETAIL/$id") }
            )
        }

        composable(
            "$TASK_DETAIL/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backstackEntry ->
            backstackEntry.arguments?.getInt("taskId")?.let {
                TaskDetailScreen(
                    onClick = { id -> navController.navigate("$ADD_EDIT/$id") },
                    onBack = { navController.popBackStack() },
                    taskId = it
                )
            }
        }

        composable(ADD_EDIT) {
            TaskAddEditScreen(
                onClick = { navController.navigate(TASKS_LIST){
                    popUpTo(TASKS_LIST) { inclusive = true } } },
                onBack = { navController.popBackStack() },
                taskId = -1
            )
        }

        composable(
            "$ADD_EDIT/{detailId}",
            arguments = listOf(navArgument("detailId") { type = NavType.IntType })
        ) { backstackEntry ->
            val detailId = backstackEntry.arguments?.getInt("detailId") ?: -1

            TaskAddEditScreen(
                onClick = { navController.navigate(TASKS_LIST) {
                    popUpTo(TASKS_LIST) { inclusive = true } }},
                onBack = { navController.popBackStack() },
                taskId = detailId
            )
        }
    }
}

const val TASKS_LIST = "tasksList"
const val TASK_DETAIL = "taskDetail"
const val ADD_EDIT = "addEdit"