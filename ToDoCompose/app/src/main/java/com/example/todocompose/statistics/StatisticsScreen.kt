package com.example.todocompose.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
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
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
){
    val activeTaskPercent = viewModel.activeTaskPercent.observeAsState()
    val completedTaskPercent = viewModel.completedTaskPercent.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.statistics_screen)) }
            )
        }
    ){ innerPadding ->
        StatisticsScreenContent(
            activeTaskPercent = activeTaskPercent.value,
            completedTaskPercent = completedTaskPercent.value,
            modifier = Modifier.padding(innerPadding)
        )
    }
    
}

@Composable
fun StatisticsScreenContent(
    activeTaskPercent: Float?,
    completedTaskPercent: Float?,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Text(text = stringResource(id = R.string.active_task_percent, activeTaskPercent ?: 0.0f))
        Text(text = stringResource(id = R.string.completed_task_percent, completedTaskPercent ?: 0.0f))
    }
}

@Preview
@Composable
fun StatisticsScreenPreview(){
    ToDoComposeTheme() {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            StatisticsScreenContent(
                50.0f, 50.0f
            )
        }
    }
}