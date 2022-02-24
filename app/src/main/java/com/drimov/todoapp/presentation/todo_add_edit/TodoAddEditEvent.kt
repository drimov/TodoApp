package com.drimov.todoapp.presentation.todo_add_edit

sealed class TodoAddEditEvent {
    data class OnTitleChange(val title: String): TodoAddEditEvent()
    data class OnDescriptionChange(val description: String): TodoAddEditEvent()
    object OnSaveTodo: TodoAddEditEvent()
}