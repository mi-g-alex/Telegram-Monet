package com.c3r5b8.telegram_monet

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.c3r5b8.telegram_monet.presentation.how_to_use.HowToUseScreen
import com.c3r5b8.telegram_monet.presentation.main_screen.MainScreen
import com.c3r5b8.telegram_monet.presentation.main_screen.MainScreenViewModel


private object Routes {
    const val MAIN_ROUTE = "MAIN_ROUTE"
    const val MAIN_SCREEN = "MAIN_SCREEN"
    const val HOW_USE_SCREEN = "HOW_USE_SCREEN"
}

@Composable
fun NavigationScreen(
    navController: NavHostController = rememberNavController()
) {

    val mainScreenViewModel = MainScreenViewModel(LocalContext.current)

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN_ROUTE
    ) {

        navigation(
            route = Routes.MAIN_ROUTE,
            startDestination = Routes.MAIN_SCREEN
        ) {
            composable(
                route = Routes.MAIN_SCREEN,
            ) { _ ->
                MainScreen(
                    viewModel = mainScreenViewModel
                ) {
                    navController.navigate(Routes.HOW_USE_SCREEN)
                }
            }
            composable(
                route = Routes.HOW_USE_SCREEN,
            ) { _ ->
                HowToUseScreen { navController.navigateUp() }
            }
        }
    }

}