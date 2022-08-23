package com.example.todocompose.taskdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todocompose.R
import com.example.todocompose.ui.theme.ToDoComposeTheme

@Composable
fun TaskDetailScreen(
    taskId: Int,
    onClick: (Int) -> Unit,
    viewModel: TaskDetailViewModel = hiltViewModel()
) {
//    val task = viewModel.getTask(taskId).observeAsState()
    viewModel.updateTaskId(taskId)
    val task = viewModel.currentTask.observeAsState()

    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = stringResource(id = R.string.task_detail_screen)) }
        )},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    task.value?.let { onClick(it.id) }
                }
            ) {
                Icon(Icons.Default.Edit, contentDescription = "edit task")
            }
        }
    ) { innerPadding ->
        task.value?.let {
            TaskDetailScreenContent(
                taskTitle = it.title,
                taskDescription = it.description,
                modifier = Modifier.padding(innerPadding)
            )
        }
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
