package com.drimov.todoapp.presentation.todo_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drimov.todoapp.domain.model.Todo
import com.drimov.todoapp.domain.repository.TodoRepository
import com.drimov.todoapp.util.ResourcesHelper
import com.drimov.todoapp.util.Routes
import com.drimov.todoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val helper: ResourcesHelper
) : ViewModel() {

    val todos = repository.getTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deleteTodo: Todo? = null

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnAddTodoClick -> {
                Log.d("Add","event")
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnTodoClick -> {
                Log.d("onDo","event")
                Log.d("onDo ID","${event.todo.id}")
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnDeleteTodo -> {
                viewModelScope.launch {
                    deleteTodo = event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            message = helper.message_delete,
                            action = helper.action
                        )
                    )
                }
            }
            is TodoListEvent.OnUndoDeleteClick -> {
                deleteTodo?.let { todo ->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
                }
            }

            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(isDone = event.isDone)
                    )
                }

            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}