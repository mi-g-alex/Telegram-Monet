package com.c3r5b8.telegram_monet

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.c3r5b8.telegram_monet.presentation.image_palette.ImagePaletteScreen
import com.c3r5b8.telegram_monet.presentation.main_screen.MainScreen
import com.c3r5b8.telegram_monet.ui.theme.TelegramMonetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TelegramMonetTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val popDuration =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) 200
                        else 300
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        enterTransition = {
                            slideInHorizontally(tween(300)) { it } + fadeIn(tween(300))
                        },
                        exitTransition = {
                            slideOutHorizontally(tween(300)) { -it / 4 } + fadeOut(tween(300))
                        },
                        popEnterTransition = {
                            slideInHorizontally(tween(popDuration)) { -it / 4 } + fadeIn(tween(popDuration))
                        },
                        popExitTransition = {
                            slideOutHorizontally(tween(popDuration)) { it } + fadeOut(tween(popDuration))
                        },
                    ) {
                        composable("main") {
                            MainScreen(
                                onOpenImagePalette = { navController.navigate("image_palette") },
                                onEditPalette = { paletteId -> navController.navigate("image_palette/$paletteId") },
                            )
                        }
                        composable("image_palette") {
                            ImagePaletteScreen(
                                onBack = { navController.popBackStack() },
                            )
                        }
                        composable("image_palette/{paletteId}") { backStackEntry ->
                            val paletteId = backStackEntry.arguments
                                ?.getString("paletteId")
                                ?.toIntOrNull() ?: -1
                            ImagePaletteScreen(
                                onBack = { navController.popBackStack() },
                                paletteId = paletteId,
                            )
                        }
                    }
                }
            }
        }
    }
}
