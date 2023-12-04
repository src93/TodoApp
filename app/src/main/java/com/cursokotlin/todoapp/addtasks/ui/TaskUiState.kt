package com.cursokotlin.todoapp.addtasks.ui

import com.cursokotlin.todoapp.addtasks.ui.model.TaskModel

sealed interface TaskUiState {
    object Loading: TaskUiState
    data class Error(val throwable: Throwable): TaskUiState
    data class Success(val tasks: List<TaskModel>): TaskUiState
}
