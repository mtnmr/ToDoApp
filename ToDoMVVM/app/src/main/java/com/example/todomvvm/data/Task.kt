package com.example.todomvvm.data

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val isChecked : Boolean = false
)
