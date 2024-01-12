package com.crocheteer.crocheteer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            YarnStash(
                onNavigateToDetails = { navController.navigate("${Screens.YarnDetailsScreen.name}/${it}") },
                onNavigateToYarnAdd = { navController.navigate(Screens.AddYarnScreen.name) })
        }

        composable(
            "${Screens.YarnDetailsScreen.name}/{yarnId}",
            arguments = listOf(navArgument("yarnId") { type = NavType.LongType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong("yarnId")?.let {
                YarnDetailsScreen(
                    yarnId = it,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
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
