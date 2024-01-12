package com.crocheteer.crocheteer.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FloatingActionButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    androidx.compose.material3.FloatingActionButton(
        onClick = onClick,
        modifier = modifier.padding(16.dp),
        content = {
            Icon(Icons.Filled.Add, contentDescription = "Add Yarn")
        }
    )
}