package com.example.todocompose.addedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todocompose.R
import com.example.todocompose.ui.theme.ToDoComposeTheme

@Composable
fun TaskAddEditScreen(
    onClick:(String, String) -> Unit
){
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onClick(taskTitle, taskDescription)
                    taskTitle = ""
                    taskDescription = ""
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = "add task")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextField(
                value = taskTitle,
                onValueChange = { taskTitle = it},
                label = { Text(text = stringResource(id = R.string.task_title))},
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                singleLine = true
            )

            TextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                label = { Text(text = stringResource(id = R.string.task_description))},
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun TaskAddEditScreenPreview(){
    ToDoComposeTheme {
        TaskAddEditScreen(
            onClick = {_,_ -> }
        )
    }
}