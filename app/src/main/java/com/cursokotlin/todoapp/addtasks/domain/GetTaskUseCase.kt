package com.cursokotlin.todoapp.addtasks.domain

import com.cursokotlin.todoapp.addtasks.data.TaskRepository
import com.cursokotlin.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> {
        return taskRepository.tasks
    }
}