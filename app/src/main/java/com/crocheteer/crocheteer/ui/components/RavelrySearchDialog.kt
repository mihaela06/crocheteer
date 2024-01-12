package com.crocheteer.crocheteer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RavelrySearchDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (YarnType) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var searchTerm by rememberSaveable { mutableStateOf("") }
                var searchBarText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                    mutableStateOf(TextFieldValue(""))
                }
                var yarnType by rememberSaveable {
                    mutableStateOf<YarnType?>(null)
                }

                Row(modifier = modifier.fillMaxWidth()) {
                    TextField(
                        value = searchBarText,
                        onValueChange = { searchBarText = it },
                        modifier = modifier.fillMaxWidth(0.8f)
                    )
                    IconButton(
                        onClick = {
                            searchTerm = searchBarText.text
                        },
                        modifier = modifier.fillMaxWidth(0.2f)
                    )
                    {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search",
                            modifier.fillMaxWidth()
                        )
                    }
                }
                YarnSearchList(
                    searchTerm = searchTerm,
                    onSelect = { yarnType = it },
                    modifier = modifier.fillMaxHeight(0.7f)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onConfirmation(yarnType!!) },
                        modifier = Modifier.padding(8.dp),
                        enabled = yarnType != null
                    ) {
                        Text("Confirm")
                    }
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
    if (searchTerm == "") return Text("Enter your text to search")

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
    Box(modifier = modifier.fillMaxSize()) {
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
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            count = yarnPagingItems.itemCount,
            key = yarnPagingItems.itemKey { it.second },
        ) { index ->
            if (yarnPagingItems[index] == null) return@items
            val (yarn, _) = yarnPagingItems[index]!!
            Card(
                modifier = modifier.clickable {
                    onSelect(yarn)

                    // TODO change background
                }
            ) {
                Text(text = yarn.companyName + " " + yarn.name)
            }
            Divider(
                color = MaterialTheme.colorScheme.secondary,
                thickness = 0.2.dp,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
        }

        item {
            if (yarnPagingItems.loadState.append is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}
