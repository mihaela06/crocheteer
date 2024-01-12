package com.crocheteer.crocheteer.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.crocheteer.crocheteer.ui.components.NavigateBackIcon
import com.crocheteer.crocheteer.ui.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnDetailsScreen(onNavigateBack: () -> Unit, modifier: Modifier = Modifier){
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(text = "Yarn Details",
                navigationIcon = {
                    NavigateBackIcon(
                        onNavigateBack, "Go back to yarn stash"
                    )
                })
        },
    ) {
        Box(
            modifier = modifier
                .padding(top = 100.dp)
                .background(Color.Transparent)
        ) {

        }
    }
}