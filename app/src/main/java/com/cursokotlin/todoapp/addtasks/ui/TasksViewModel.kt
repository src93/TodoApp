package com.cursokotlin.todoapp.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.todoapp.addtasks.domain.AddTaskUseCase
import com.cursokotlin.todoapp.addtasks.domain.DeleteTaskUseCase
import com.cursokotlin.todoapp.addtasks.domain.GetTaskUseCase
import com.cursokotlin.todoapp.addtasks.domain.UpdateTaskUseCase
import com.cursokotlin.todoapp.addtasks.ui.TaskUiState.*
import com.cursokotlin.todoapp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    getTaskUseCase: GetTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    val uiState: StateFlow<TaskUiState> = getTaskUseCase().map(::Success).catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: MutableLiveData<Boolean> = _showDialog
    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks = _tasks

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        _showDialog.value = false
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onFabIconClicked() {
        _showDialog.value = true
    }

    fun onCheckBoxChange(taskModel: TaskModel) {
        changeStateSelected(taskModel = taskModel)
    }

    fun onLongPressTask(taskModel: TaskModel) {
        deleteItem(taskModel)
    }

    private fun changeStateSelected(taskModel: TaskModel) {
        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }
    }

    private fun deleteItem(taskModel: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase(taskModel = taskModel)
        }
    }
}