package com.example.todocompose.taskslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todocompose.R
import com.example.todocompose.data.Task
import com.example.todocompose.ui.theme.ToDoComposeTheme

@Composable
fun TasksListScreen(
    onClick:() -> Unit,
    onItemClick: (Int) -> Unit,
    viewModel: TasksListViewModel = hiltViewModel()
){

    val todoList = viewModel.allTasks.observeAsState()

    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) }
        )},
        floatingActionButton = {
            FloatingActionButton(onClick = onClick) {
                Icon(Icons.Default.Add, contentDescription = "add task")
            }
        },
        bottomBar = {
            BottomNavigation() {

            }
        }
    ) { innerPadding ->
        todoList.value?.let {
            TasksListScreenContent(
                tasks = it,
                onCheckedChange = { id, b ->  viewModel.updateChecked(id, b)},
                onItemClick = {id -> onItemClick(id)},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun TasksListScreenContent(
    tasks : List<Task>,
    onCheckedChange:(Int, Boolean) -> Unit,
    onItemClick:(Int) -> Unit,
    modifier: Modifier = Modifier
){

    Column(modifier = modifier) {
        LazyColumn(){
            items(
                items = tasks,
                key = {task -> task.id}
            ){ task ->
                TaskItemScreen(
                    taskTitle = task.title,
                    checked = task.isChecked,
                    onCheckedChange = { b -> onCheckedChange(task.id, b)},
                    onItemClick = { onItemClick(task.id)}
                )
            }
        }
    }
}

@Composable
fun TaskItemScreen(
    taskTitle:String,
    checked:Boolean,
    onCheckedChange:(Boolean) -> Unit,
    onItemClick:() -> Unit
){
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {b: Boolean -> onCheckedChange(b)}
        )

        Text(
            text = taskTitle,
            modifier = Modifier.weight(1f),
            fontSize = 24.sp
        )
    }
}

@Preview
@Composable
fun TaskItemScreenPreview(){
    ToDoComposeTheme {
        Surface() {
            TaskItemScreen(
                "task title",
                checked = false,
                onCheckedChange = { },
                onItemClick = {}
            )
        }
    }
}

@Preview
@Composable
fun TasksListScreenPreview(){
    ToDoComposeTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            TasksListScreenContent(
                sampleTasksList,
                onCheckedChange = { id, b -> },
                onItemClick = {}
            )
        }
    }
}

val sampleTasksList = listOf(
    Task(id = 1, title = "task1"),
    Task(id = 2, title = "task2"),
    Task(id = 3, title = "task3")
)