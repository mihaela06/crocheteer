package com.crocheteer.crocheteer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crocheteer.crocheteer.screens.YarnStash
import com.crocheteer.crocheteer.screens.SplashScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ){
        composable("splash_screen"){
            SplashScreen(navController = navController)
        }

        composable("main_screen"){
            YarnStash(navController = navController)
        }
    }

}
