package com.cursokotlin.todoapp.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursokotlin.todoapp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor() : ViewModel() {
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: MutableLiveData<Boolean> = _showDialog
    private val _tasks = mutableStateListOf<TaskModel>()
    val task = _tasks

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        _showDialog.value = false
        _tasks.add(TaskModel(task = task))
        Log.i("Sergio", task)
    }

    fun onFabIconClicked() {
        _showDialog.value = true
    }

    fun onCheckBoxChange(taskModel: TaskModel) {
        addItem(taskModel = taskModel)
    }

    fun onLongPressTask(taskModel: TaskModel) {
        deleteItem(taskModel)
    }

    private fun addItem(taskModel: TaskModel) {
        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }
    }

    private fun deleteItem(taskModel: TaskModel) {
        val item = _tasks.find { it.id == taskModel.id }
        _tasks.remove(item)
    }
}