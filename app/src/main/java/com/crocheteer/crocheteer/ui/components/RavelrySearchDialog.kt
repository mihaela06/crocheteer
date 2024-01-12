package com.crocheteer.crocheteer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.crocheteer.crocheteer.data.entities.YarnType
import com.crocheteer.crocheteer.ui.viewmodels.YarnSearchListViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RavelrySearchDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (YarnType) -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(onDismissRequest = { onDismissRequest() }) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 50.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var searchTerm by rememberSaveable { mutableStateOf("") }
                var searchBarText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                    mutableStateOf(TextFieldValue(""))
                }
                var yarnType by rememberSaveable {
                    mutableStateOf<YarnType?>(null)
                }
                Spacer(modifier = modifier.height(16.dp))
                Row(modifier = modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = searchBarText,
                        onValueChange = { searchBarText = it },
                        modifier = modifier.fillMaxWidth(),
                        label = { Text("Enter your text to search") },
                        singleLine = true,
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    searchTerm = searchBarText.text
                                    keyboardController?.hide()
                                }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search",
                                )
                            }
                        }
                    )
                }
                YarnSearchList(
                    searchTerm = searchTerm,
                    onSelect = {
                        yarnType = it
                    },
                    modifier = modifier
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onConfirmation(yarnType!!) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(50),
                    enabled = yarnType != null
                ) {
                    Text("Confirm", color = Color.White)
                }
            }
        }
    }
}


@Composable
fun YarnSearchList(
    searchTerm: String,
    onSelect: (YarnType) -> Unit,
    modifier: Modifier = Modifier
) {
    if (searchTerm == "") return Text("")

    val viewModel = hiltViewModel<YarnSearchListViewModel>()
    val yarnPagingItems = viewModel.yarnSearchPagingDataFlow(searchTerm).collectAsLazyPagingItems()

    YarnList(yarnPagingItems, onSelect, modifier)
}

@Composable
fun YarnList(
    yarnPagingItems: LazyPagingItems<Pair<YarnType, Int>>,
    onSelect: (YarnType) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (yarnPagingItems.loadState.refresh is LoadState.Loading)
            CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
        else
            YarnColumn(yarnPagingItems, onSelect, modifier)
    }
}

@Composable
fun YarnColumn(
    yarnPagingItems: LazyPagingItems<Pair<YarnType, Int>>,
    onSelect: (YarnType) -> Unit,
    modifier: Modifier = Modifier
) {
    var isSelected by remember { mutableStateOf(mapOf<Int, Boolean>()) }
    LazyColumn(
//        modifier = modifier.height(300.dp),
        modifier = modifier.fillMaxHeight(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            count = yarnPagingItems.itemCount,
            key = yarnPagingItems.itemKey { it.second },
        ) { index ->
            if (yarnPagingItems[index] == null) return@items
            val (yarn, id) = yarnPagingItems[index]!!

            Card(
                modifier = modifier
                    .padding(6.dp)
                    .clickable(
                        onClick = {
                            onSelect(yarn)
                            isSelected = isSelected
                                .toMutableMap()
                                .apply {
                                    put(id, this[id] ?: true)
                                }
                        }
                    ),
                colors = if (isSelected[id] == true) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary) else CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                YarnMainInfo(yarnType = yarn)
            }
        }

        item {
            if (yarnPagingItems.loadState.append is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}
