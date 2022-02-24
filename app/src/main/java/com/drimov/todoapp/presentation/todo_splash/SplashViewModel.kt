package com.drimov.todoapp.presentation.todo_splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drimov.todoapp.util.Routes
import com.drimov.todoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        onMainPage()
    }

    private fun onMainPage() {
        viewModelScope.launch {
            delay(3000L)
            _uiEvent.send(UiEvent.Navigate(Routes.LIST_TODO))
        }
    }
}