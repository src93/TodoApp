package com.cursokotlin.todoapp.addtasks.domain

import com.cursokotlin.todoapp.addtasks.data.TaskRepository
import com.cursokotlin.todoapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.delete(taskModel = taskModel)
    }
}