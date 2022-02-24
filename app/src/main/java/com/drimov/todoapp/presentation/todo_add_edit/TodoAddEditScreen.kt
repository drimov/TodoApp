package com.drimov.todoapp.presentation.todo_add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drimov.todoapp.R
import com.drimov.todoapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun TodoAddEditScreen(
    onPopBackStack: () -> Unit,
    viewModel: TodoAddEditViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(TodoAddEditEvent.OnSaveTodo)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(id = R.string.save)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Row{
                Column {
                    Text(text = "Title")
                    TextField(
                        value = viewModel.title,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { viewModel.onEvent(TodoAddEditEvent.OnTitleChange(it)) }
                    )
                }

            }
            Row {
                Column {
                    Text(text = "Description")
                    TextField(
                        value = viewModel.description,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { viewModel.onEvent(TodoAddEditEvent.OnDescriptionChange(it)) })
                }

            }


        }
    }
}