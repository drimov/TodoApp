package com.drimov.todoapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.drimov.todoapp.presentation.todo_add_edit.TodoAddEditScreen
import com.drimov.todoapp.presentation.todo_list.TodoListScreen
import com.drimov.todoapp.presentation.todo_splash.SplashScreen
import com.drimov.todoapp.util.Routes

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH_TODO
    ) {
        composable(route = Routes.SPLASH_TODO) {
            SplashScreen(onNavigate = { navController.navigate(it.route) })
        }
        composable(route = Routes.LIST_TODO) {
            TodoListScreen(onNavigate = { navController.navigate(it.route) })
        }
        composable(
            route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
            arguments = listOf(
                navArgument(name = "todoId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            TodoAddEditScreen(onPopBackStack = { navController.popBackStack() })
        }
    }
}