package com.crocheteer.crocheteer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crocheteer.crocheteer.screens.YarnStash
import com.crocheteer.crocheteer.screens.SplashScreen
import com.crocheteer.crocheteer.screens.YarnDetailsScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.name
    ){
        composable(Screens.SplashScreen.name){
            SplashScreen(navController = navController)
        }

        composable(Screens.MainScreen.name){
            YarnStash(navController = navController)
        }
        
        composable(Screens.YarnDetailsScreen.name){
            YarnDetailsScreen(navController = navController)
        }
    }

}
