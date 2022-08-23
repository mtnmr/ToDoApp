package com.example.todocompose.addedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todocompose.R
import com.example.todocompose.ui.theme.ToDoComposeTheme

@Composable
fun TaskAddEditScreen(
    onClick: () -> Unit
) {

    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = stringResource(id = R.string.add_edit_task_screen)) }
        )},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onClick()
                    taskTitle = ""
                    taskDescription = ""
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = "add task")
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