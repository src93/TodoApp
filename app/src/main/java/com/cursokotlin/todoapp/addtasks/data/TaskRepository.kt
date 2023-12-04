package com.cursokotlin.todoapp.addtasks.data

import com.cursokotlin.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    val tasks: Flow<List<TaskModel>> = taskDao.getTasks().map { items ->
        items.map {
            TaskModel(
                id = it.id,
                task = it.task,
                selected = it.selected
            )
        }
    }

    suspend fun add(taskModel: TaskModel) {
        taskDao.addTask(
            TaskEntity(
                id = taskModel.id,
                task = taskModel.task,
                selected = taskModel.selected
            )
        )
    }

    suspend fun update(taskModel: TaskModel) {
        taskDao.updateTask(
            TaskEntity(
                id = taskModel.id,
                task = taskModel.task,
                selected = taskModel.selected
            )
        )
    }

    suspend fun delete(taskModel: TaskModel) {
        taskDao.deleteTask(
            TaskEntity(
                id = taskModel.id,
                task = taskModel.task,
                selected = taskModel.selected
            )
        )
    }
}