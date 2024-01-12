package com.crocheteer.crocheteer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crocheteer.crocheteer.screens.AddYarnScreen
import com.crocheteer.crocheteer.screens.YarnDetailsScreen
import com.crocheteer.crocheteer.screens.YarnStash

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.name
    ){
        composable(Screens.MainScreen.name) {
            YarnStash(navController = navController)
        }

        composable(Screens.YarnDetailsScreen.name) {
            YarnDetailsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(
            Screens.AddYarnScreen.name
        ) {
            AddYarnScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
