package com.example.todocompose.taskdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
    onBack: () -> Unit,
    viewModel: TaskDetailViewModel = hiltViewModel()
) {

    viewModel.updateTaskId(taskId)
    val task = viewModel.currentTask.observeAsState()

    Scaffold(
        topBar = {
            TaskDetailTopAppBar(
                onBack = onBack,
                onDelete = {
                    task.value?.let { viewModel.deleteTask(it) }
                    onBack()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    task.value?.let { onClick(it.id) }
                }
            ) {
                Icon(Icons.Default.Edit, contentDescription = stringResource(id = R.string.edit_task_fab))
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

@Composable
fun TaskDetailTopAppBar(
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.task_detail_screen)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.menu_back))
            }
        },
        modifier = Modifier.fillMaxWidth(),
        actions = {
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = stringResource(id = R.string.delete_task_icon))
            }
        }

    )
}

@Preview
@Composable
fun TaskDetailTopAppBarPreview() {
    ToDoComposeTheme() {
        Surface {
            TaskDetailTopAppBar(
                onBack = {},
                onDelete = {}
            )
        }
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
