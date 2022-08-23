package com.example.todocompose.taskdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todocompose.R
import com.example.todocompose.ui.theme.ToDoComposeTheme

@Composable
fun TaskDetailScreen(
    onClick: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = stringResource(id = R.string.task_detail_screen)) }
        )},
        floatingActionButton = {
            FloatingActionButton(onClick = onClick) {
                Icon(Icons.Default.Edit, contentDescription = "edit task")
            }
        }
    ) { innerPadding ->
        TaskDetailScreenContent(
            taskTitle = "",
            taskDescription = "",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TaskDetailScreenContent(
    taskTitle: String,
    taskDescription: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = taskTitle,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Text(
            text = taskDescription,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun TaskDetailScreenPreview() {
    ToDoComposeTheme() {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            TaskDetailScreenContent(
                taskTitle = "title",
                taskDescription = "description"
            )
        }
    }
}
