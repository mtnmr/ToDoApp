package com.example.todocompose.addedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todocompose.R
import com.example.todocompose.ui.theme.ToDoComposeTheme

@Composable
fun TaskAddEditScreen(
    taskId:Int,
    onClick: () -> Unit,
    onBack: () -> Unit,
    viewModel: TaskAddViewModel = hiltViewModel()
) {

    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    if (taskId != -1){
        viewModel.updateTaskId(taskId)
        val task = viewModel.currentTask.observeAsState()
        taskTitle = task.value?.title ?: ""
        taskDescription = task.value?.description ?: ""
    }

    Scaffold(
        topBar = {
            TaskAddEditTopAppBar(
                  onBack = onBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.addOrEditTask(taskId, taskTitle, taskDescription)
                    onClick()
                    taskTitle = ""
                    taskDescription = ""
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = stringResource(id = R.string.save_task_fab))
            }
        }
    ) { innerPadding ->

        TaskAddEditScreenContent(
            taskTitle = taskTitle,
            onTitleChanged = { taskTitle = it },
            taskDescription = taskDescription,
            onDescriptionChanged = { taskDescription = it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TaskAddEditScreenContent(
    taskTitle: String,
    onTitleChanged: (String) -> Unit,
    taskDescription: String,
    onDescriptionChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        TextField(
            value = taskTitle,
            onValueChange = onTitleChanged,
            label = { Text(text = stringResource(id = R.string.task_title)) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            singleLine = true
        )

        TextField(
            value = taskDescription,
            onValueChange = onDescriptionChanged,
            label = { Text(text = stringResource(id = R.string.task_description)) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun TaskAddEditTopAppBar(
    onBack: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.add_edit_task_screen)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.menu_back))
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Preview
@Composable
fun TaskAddEditTopAppBarPreview() {
    ToDoComposeTheme() {
        Surface {
            TaskAddEditTopAppBar(
                onBack = {}
            )
        }
    }
}

@Preview
@Composable
fun TaskAddEditScreenPreview() {
    ToDoComposeTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            TaskAddEditScreenContent(
                taskTitle = "",
                onTitleChanged = { },
                taskDescription = "",
                onDescriptionChanged = { }
            )
        }
    }
}