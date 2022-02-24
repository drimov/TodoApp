package com.drimov.todoapp.presentation.todo_add_edit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drimov.todoapp.domain.model.Todo
import com.drimov.todoapp.domain.repository.TodoRepository
import com.drimov.todoapp.util.ResourcesHelper
import com.drimov.todoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoAddEditViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val helper: ResourcesHelper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    var todo by mutableStateOf<Todo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")
        Log.d("id","$todoId")
        if (todoId != null) {
            viewModelScope.launch {
                repository.getTodoById(todoId!!)?.let { todo ->
                    title = todo.title
                    description = todo.description ?: ""
                    this@TodoAddEditViewModel.todo = todo
                }

            }
        }
    }

    fun onEvent(event: TodoAddEditEvent) {
        when (event) {
            is TodoAddEditEvent.OnSaveTodo -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                message = helper.message_title_need
                            )
                        )
                        return@launch
                    }
                    repository.insertTodo(
                        Todo(
                            id = todo?.id ?: 0,
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
            is TodoAddEditEvent.OnDescriptionChange -> {
                description = event.description
            }
            is TodoAddEditEvent.OnTitleChange -> {
                title = event.title
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}