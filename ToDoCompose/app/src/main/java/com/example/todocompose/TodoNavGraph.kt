package com.example.todocompose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todocompose.addedit.TaskAddEditScreen
import com.example.todocompose.statistics.StatisticsScreen
import com.example.todocompose.taskdetail.TaskDetailScreen
import com.example.todocompose.taskslist.TasksListScreen

@Composable
fun TodoNavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            TodoBottomNavigation(navController = navController)
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = TASKS_LIST,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TASKS_LIST) {
                TasksListScreen(
                    onFabClick = { navController.navigate(ADD_EDIT) },
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

            composable(STATISTICS){
                StatisticsScreen()
            }
        }
    }

}

const val TASKS_LIST = "tasksList"
const val TASK_DETAIL = "taskDetail"
const val ADD_EDIT = "addEdit"
const val STATISTICS = "statistics"

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
){
    object TaskScreen : Screen(TASKS_LIST, R.string.task_screen, Icons.Filled.Edit)
    object StatisticsScreen : Screen(STATISTICS, R.string.statistics_screen, Icons.Filled.Star)
}

val items = listOf(Screen.TaskScreen, Screen.StatisticsScreen)

@Composable
fun TodoBottomNavigation(
    navController: NavController
){
    BottomNavigation() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach{ screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}