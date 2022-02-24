package com.drimov.todoapp.presentation.todo_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.drimov.todoapp.R
import com.drimov.todoapp.domain.model.Todo

@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoListEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        Column(modifier = modifier.weight(1f)) {
            Row (verticalAlignment = Alignment.CenterVertically){
                Text(text = todo.title)
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    onEvent(TodoListEvent.OnDeleteTodo(todo))
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete)
                    )
                }
            }
            todo.description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it)
            }
        }

        Checkbox(
            checked = todo.isDone,
            onCheckedChange = { isChecked ->
                onEvent(TodoListEvent.OnDoneChange(todo, isChecked))
            }
        )
    }

}
