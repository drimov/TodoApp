package com.drimov.todoapp.presentation.todo_splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.drimov.todoapp.R
import com.drimov.todoapp.presentation.ui.theme.Background
import com.drimov.todoapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun SplashScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val rotation = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {

        rotation.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(.5f).getInterpolation(it)
                }
            )
        )
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.logo),
            modifier = Modifier
                .fillMaxSize()
                .rotate(rotation.value)
        )
    }
}