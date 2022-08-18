package com.example.todocompose.taskslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todocompose.R
import com.example.todocompose.data.Task
import com.example.todocompose.ui.theme.ToDoComposeTheme

@Composable
fun TasksListScreen(
    tasks : List<Task>,
    onClick:() -> Unit
){
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
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(){
                items(
                    items = tasks,
                    key = {task -> task.id}
                ){ task ->
                    TaskItemScreen(
                        taskTitle = task.title,
                        onCheckedChange = {},
                        onItemClick = { },
                        deleteOnClick = { }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItemScreen(
    taskTitle:String,
    onCheckedChange:(Boolean) -> Unit,
    onItemClick:() -> Unit,
    deleteOnClick:() -> Unit
){
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Checkbox(
            checked = false,
            onCheckedChange = onCheckedChange
        )

        Text(
            text = taskTitle,
            modifier = Modifier.weight(1f),
            fontSize = 24.sp
        )

        IconButton(onClick = deleteOnClick) {
            Icon(Icons.Default.Delete, contentDescription = "")
        }
    }
}

@Preview
@Composable
fun TaskItemScreenPreview(){
    ToDoComposeTheme {
        Surface() {
            TaskItemScreen(
                "task title",
                onCheckedChange = { },
                onItemClick = {},
                deleteOnClick = {}
            )
        }
    }
}

@Preview
@Composable
fun TasksListScreenPreview(){
    ToDoComposeTheme {
        TasksListScreen(sampleTasksList, onClick = {})
    }
}

val sampleTasksList = listOf(
    Task(id = 1, title = "task1"),
    Task(id = 2, title = "task2"),
    Task(id = 3, title = "task3")
)