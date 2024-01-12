package com.crocheteer.crocheteer.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.crocheteer.crocheteer.navigation.Screens
import com.crocheteer.crocheteer.ui.components.CollapsibleSearchBar
import com.crocheteer.crocheteer.ui.components.FloatingActionButton
import com.crocheteer.crocheteer.ui.components.StashedYarnList
import com.crocheteer.crocheteer.ui.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnStash(navController: NavController, modifier: Modifier = Modifier) {
    var searchTerm by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(text = "My Yarn Stash", actions = {
                CollapsibleSearchBar(
                    onSearchTermChange = { searchTerm = it },
                    modifier = modifier
                )
            })
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screens.AddYarnScreen.name) })
        }
    ) {
        Box(
            modifier = modifier
                .padding(top = 100.dp)
                .background(Color.Transparent)
        ) {
            StashedYarnList(
                searchTerm = searchTerm,
                onNavigateToDetails = { navController.navigate(Screens.YarnDetailsScreen.name) },
                modifier = modifier
            )
        }
    }
}