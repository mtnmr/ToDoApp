package com.example.todocompose.taskslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
    onFabClick:() -> Unit,
    onItemClick: (Int) -> Unit,
    viewModel: TasksListViewModel = hiltViewModel()
){

    val todoList = viewModel.showTasksList.observeAsState()
    val taskFilter = viewModel.taskFilter.observeAsState()

    Scaffold(
        topBar = {
            TasksListTopAppBar(
                onActiveTaskClick = {viewModel.taskFilterChange(TaskFilter.ACTIVE_TASK)},
                onCompletedTaskClick = {viewModel.taskFilterChange(TaskFilter.COMPLETED_TASK)},
                onAllTaskClick = { viewModel.taskFilterChange(TaskFilter.ALL_TASK)}
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFabClick) {
                Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add_task_fab))
            }
        }
    ) { innerPadding ->
        todoList.value?.let {
            TasksListScreenContent(
                taskFilterId = taskFilter,
                tasks = it,
                onCheckedChange = { id, b ->  viewModel.updateChecked(id, b)},
                onItemClick = { id -> onItemClick(id)},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun TasksListScreenContent(
    taskFilterId: State<Int?>,
    tasks: List<Task>,
    onCheckedChange:(Int, Boolean) -> Unit,
    onItemClick:(Int) -> Unit,
    modifier: Modifier = Modifier
){

    val filterText = stringResource(id = taskFilterId.value ?: R.string.all_task)

    Column(modifier = modifier) {
        Text(
            text = filterText,
            fontSize = 30.sp,
            modifier = Modifier.padding(8.dp)
        )
        
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


@Composable
fun TasksListTopAppBar(
    onActiveTaskClick:() -> Unit,
    onCompletedTaskClick:() -> Unit,
    onAllTaskClick:() -> Unit
){
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            FilterTaskMenu(
                onActiveTaskClick = onActiveTaskClick,
                onCompletedTaskClick = onCompletedTaskClick,
                onAllTaskClick = onAllTaskClick
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun FilterTaskMenu(
    onActiveTaskClick:() -> Unit,
    onCompletedTaskClick:() -> Unit,
    onAllTaskClick:() -> Unit
){
    TopAppBarDropdownMenu(){ closeMenu ->
        DropdownMenuItem(onClick = { onActiveTaskClick() ; closeMenu() }) {
            Text(text = stringResource(id = R.string.active_task))
        }

        DropdownMenuItem(onClick = {onCompletedTaskClick() ; closeMenu()}) {
            Text(text = stringResource(id = R.string.completed_task))
        }

        DropdownMenuItem(onClick = { onAllTaskClick() ; closeMenu() }) {
            Text(text = stringResource(id = R.string.all_task))
        }
    }
}

@Composable
fun TopAppBarDropdownMenu(
    content: @Composable ColumnScope.(() -> Unit) -> Unit
){
    var expanded by remember { mutableStateOf(false) }

    Box() {
        IconButton(onClick = {expanded = !expanded}) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_filter_list_24),
                contentDescription = stringResource(id = R.string.task_menu_filter)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            content{ expanded = !expanded }
        }
    }
}


@Preview
@Composable
fun TasksListTopAppBarPreview() {
    TasksListTopAppBar(
        {},{},{}
    )
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
//            TasksListScreenContent(
//                R.string.all_task,  ここをStateにしたけどPreview直してない
//                sampleTasksList,
//                onCheckedChange = { id, b -> },
//                onItemClick = {}
//            )
        }
    }
}

val sampleTasksList = listOf(
    Task(id = 1, title = "task1"),
    Task(id = 2, title = "task2"),
    Task(id = 3, title = "task3")
)