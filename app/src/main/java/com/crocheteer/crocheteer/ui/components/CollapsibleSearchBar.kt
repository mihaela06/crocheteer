package com.crocheteer.crocheteer.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsibleSearchBar(
    onSearchTermChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var collapsed by remember {
        mutableStateOf(true)
    }

    var currentText by remember {
        mutableStateOf("")
    }

    Row(
        modifier = modifier.animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    ) {
        if (collapsed) {
            IconButton(onClick = { collapsed = false }) {
                Icon(Icons.Filled.Search, "Search stashed yarn")
            }
        } else {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp)
            ) {
                OutlinedTextField(
                    value = currentText,
                    onValueChange = { currentText = it },
                    textStyle = TextStyle(fontSize = 18.sp),
                    placeholder = { Text("Enter search term") },
                    modifier = modifier.fillMaxWidth(0.8f)
                )
                IconButton(
                    onClick = { onSearchTermChange(currentText) },
                    modifier = modifier
                ) {
                    Icon(Icons.Filled.Search, "Search yarn", modifier)
                }
                IconButton(
                    onClick = {
                        onSearchTermChange("")
                        currentText = ""
                        collapsed = true
                    },
                    modifier = modifier
                ) {
                    Icon(Icons.Filled.Close, "Collapse search bar", modifier)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CollapsibleSearchBarPreview(modifier: Modifier = Modifier) {
    val items = listOf("Red", "Green", "Rose", "Yellow")
    var searchTerm by remember {
        mutableStateOf("")
    }

    val filteredItems = items.filter { it.lowercase().contains(searchTerm) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(text = "My Yarn Stash", actions = {
                CollapsibleSearchBar(
                    onSearchTermChange = { searchTerm = it },
                    modifier = modifier
                )
            })
        }
    ) { padding ->
        Box(
            modifier = modifier
                .padding(padding)
                .background(Color.Transparent)
        ) {
            LazyColumn {
                itemsIndexed(filteredItems) { _, item ->
                    Text(text = item)
                }
            }
        }
    }
}