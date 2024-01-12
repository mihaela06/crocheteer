package com.crocheteer.crocheteer.ui.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.crocheteer.crocheteer.data.entities.YarnColor
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import com.crocheteer.crocheteer.ui.viewmodels.StashedYarnListViewModel
import kotlinx.coroutines.launch
import java.util.UUID.randomUUID

@Composable
fun StashedYarnList(
    searchTerm: String,
    onNavigateToDetails: () -> Unit,
    modifier: Modifier = Modifier
) {

    val viewModel = hiltViewModel<StashedYarnListViewModel>()

    val yarnStashPagingItems =
        viewModel.yarnStashPagingDataFlow(searchTerm).collectAsLazyPagingItems()

    val scope = rememberCoroutineScope()

    YarnStashContent(
        yarnStashPagingItems,
        onNavigateToDetails,
        { scope.launch { viewModel.updateStashedYarn(it) } },
        modifier
    )
}

@Composable
fun YarnStashContent(
    yarnStashPagingItems: LazyPagingItems<YarnTypeWithColors>,
    onNavigateToDetails: () -> Unit,
    onUpdateStashedYarn: (YarnTypeWithColors) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (yarnStashPagingItems.loadState.refresh is LoadState.Loading)
            CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
        else
            YarnStashColumn(
                yarnStashPagingItems,
                onNavigateToDetails,
                onUpdateStashedYarn,
                modifier
            )
    }
}



@Composable
fun YarnStashColumn(
    yarnStashPagingItems: LazyPagingItems<YarnTypeWithColors>,
    onNavigateToDetails: () -> Unit,
    onUpdateStashedYarn: (YarnTypeWithColors) -> Unit,
    modifier: Modifier = Modifier
) {
    fun addYarnColorToStashedYarn(
        stashedYarn: YarnTypeWithColors,
        yarnColor: YarnColor
    ): YarnTypeWithColors {
        val colors =
            if (stashedYarn.colors.isNullOrEmpty()) mutableListOf<YarnColor>() else stashedYarn.colors.toMutableList()
        colors.add(yarnColor)

        return stashedYarn.copy(colors = colors)
    }

    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            count = yarnStashPagingItems.itemCount,
            key = yarnStashPagingItems.itemKey { it.type.id },
        ) { index ->
            val stashedYarn = yarnStashPagingItems[index] ?: return@items
            ExpandableCard(
                yarnTypeWithColors = stashedYarn,
                onNavigateToDetails = onNavigateToDetails,
                onAddYarnColor = {
                    var toAddYarn = it
                    if (!it.photoUri.isNullOrEmpty()) {
                        val inputStream =
                            context.contentResolver.openInputStream(Uri.parse(it.photoUri))
                        val fileName = randomUUID().toString()
                        val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
                        inputStream?.use { input ->
                            outputStream.use { output ->
                                input.copyTo(output)
                            }
                        }
                        val newUri = Uri.fromFile(context.getFileStreamPath(fileName))
                        toAddYarn = it.copy(photoUri = newUri.toString())
                    }

                    onUpdateStashedYarn(addYarnColorToStashedYarn(stashedYarn, toAddYarn))
                },
                modifier = modifier
            )
        }
        item {
            if (yarnStashPagingItems.loadState.append is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}