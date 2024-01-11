package com.crocheteer.crocheteer.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import com.crocheteer.crocheteer.ui.viewmodels.StashedYarnListViewModel

@Composable
fun StashedYarnList(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel = hiltViewModel<StashedYarnListViewModel>()

    val yarnStashPagingItems = viewModel.yarnStashPagingDataFlow().collectAsLazyPagingItems()

    YarnStashContent(yarnStashPagingItems, modifier, navController)
}

@Composable
fun YarnStashContent(
    yarnStashPagingItems: LazyPagingItems<YarnTypeWithColors>,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (yarnStashPagingItems.loadState.refresh is LoadState.Loading)
            CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
        else
            YarnStashColumn(yarnStashPagingItems, modifier, navController)
    }
}


@Composable
fun YarnStashColumn(
    yarnStashPagingItems: LazyPagingItems<YarnTypeWithColors>,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            count = yarnStashPagingItems.itemCount,
            key = yarnStashPagingItems.itemKey { it.type.id },
        ) { index ->
            val stashedYarn = yarnStashPagingItems[index] ?: return@items
            ExpandableCard(stashedYarn, modifier, navController)
        }
        item {
            if (yarnStashPagingItems.loadState.append is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}