package com.drimov.todoapp.presentation.todo_list

import com.drimov.todoapp.domain.model.Todo

sealed class TodoListEvent{
    object OnAddTodoClick: TodoListEvent()
    object OnUndoDeleteClick: TodoListEvent()
    data class OnDeleteTodo(val todo: Todo): TodoListEvent()
    data class OnDoneChange(val todo: Todo,val isDone: Boolean): TodoListEvent()
    data class OnTodoClick(val todo: Todo): TodoListEvent()

}
