package com.crocheteer.crocheteer.screens

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.crocheteer.crocheteer.navigation.Screens
import com.crocheteer.crocheteer.ui.components.FloatingActionButton
import com.crocheteer.crocheteer.ui.components.StashedYarnList
import com.crocheteer.crocheteer.ui.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnDetailsScreen(navController: NavController, modifier: Modifier = Modifier){
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar("Yarn Details")
        },
    ) {
        Box(
            modifier = modifier
                .padding(top = 100.dp)
                .background(Color.Transparent)
        ) {
            IconButton(
                modifier = modifier
                    .alpha(0.5f),
                onClick = { navController.navigate(Screens.MainScreen.name) }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate to Main Screen"
                )
            }
        }
    }
}