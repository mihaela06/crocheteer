package com.crocheteer.crocheteer.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.crocheteer.crocheteer.ui.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar()
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            Box(
                modifier = modifier
                    .align(Alignment.Center)
                    .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(20.dp))
                    .padding(15.dp)
            ) {
                Text(
                    text = "Hello, $name!",
                    modifier = modifier
                )
            }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CrocheteerTheme {
//        Greeting("Crocheteer")
//    }
//}