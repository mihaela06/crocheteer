package com.crocheteer.crocheteer.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.crocheteer.crocheteer.R
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import com.crocheteer.crocheteer.ui.components.NavigateBackIcon
import com.crocheteer.crocheteer.ui.components.TopBar
import com.crocheteer.crocheteer.ui.viewmodels.YarnDetailsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnDetailsScreen(yarnId: Long, onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<YarnDetailsViewModel>()

    LaunchedEffect(Unit) {
        viewModel.getYarn(yarnId)
    }

    var showDeleteDialog by remember { mutableStateOf(false) }

    val yarn: YarnTypeWithColors? = viewModel.yarn

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(text = "Yarn details", navigationIcon = {
                NavigateBackIcon(
                    onNavigateBack, "Go back to yarn stash"
                )
            }, actions = {
                IconButton(onClick = { onEditClicked() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = {
                    onDeleteClicked()
                    showDeleteDialog = true
                }) {

                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            })
        },
    ) {
        Box(
            modifier = modifier
                .padding(top = 100.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (yarn != null) Column(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row {
                    AsyncImage(
                        model = yarn.type.genericPhotoUrl ?: "",
                        contentDescription = "Loaded image from url",
                        error = painterResource(id = R.drawable.placeholder),
                        modifier = modifier
                            .weight(1f)
                            .size(150.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Column(
                        modifier = modifier
                            .weight(1f)
                            .padding(5.dp)
                            .align(Alignment.CenterVertically),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = yarn.type.companyName, style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = modifier.height(16.dp))
                        Text(text = yarn.type.name, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = modifier.height(16.dp))
                        yarn.type.weight?.let { it1 ->
                            Text(
                                text = it1.prettifiedName,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    Spacer(modifier = modifier.height(15.dp))
                }

                Divider(modifier = modifier.padding(top = 16.dp, bottom = 16.dp))

                DetailItem(
                    label = "No. of colors in stash",
                    value = if (yarn.colors == null) "0" else yarn.colors.size.toString()
                )
                DetailItem(label = "Length", value = yarn.type.length.toString())
                DetailItem(label = "Weight", value = yarn.type.grams.toString())
                DetailItem(
                    label = "Machine washable", value = when (yarn.type.machineWashable) {
                        null -> "Unknown"
                        false -> "False"
                        true -> "True"
                    }
                )

                Divider(modifier = modifier.padding(top = 16.dp, bottom = 16.dp))
                if (yarn.colors != null) LazyColumn {
                    itemsIndexed(yarn.colors) { _, it ->
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (it.photoUri.isNullOrEmpty()) Image(
                                painter = painterResource(id = R.drawable.placeholder),
                                contentDescription = "Placeholder",
                                modifier = modifier
                                    .size(width = 50.dp, height = 50.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            else AsyncImage(
                                model = Uri.parse(it.photoUri),
                                contentDescription = "Yarn color",
                                contentScale = ContentScale.Crop,
                                error = painterResource(id = R.drawable.placeholder),
                                modifier = modifier
                                    .size(width = 50.dp, height = 50.dp)
                                    .clip(RoundedCornerShape(10.dp))

                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                it.colorCode?.let { it1 ->
                                    Text(
                                        text = it1, modifier = modifier.padding(start = 5.dp)
                                    )
                                }
                                Spacer(modifier = modifier.width(8.dp))
                                Text(text = "-")
                                Spacer(modifier = modifier.width(8.dp))
                                Text(text = it.colorName)
                                Spacer(modifier = modifier.weight(1f))
                                Text(text = "${it.quantity} g")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        DeleteDialog(
            onDismiss = { showDeleteDialog = false },
            onDelete = {
                if (yarn != null) {
                    viewModel.deleteYarn(yarn.type)
                }
                showDeleteDialog = false
                onNavigateBack()
            })
    }
}


@Composable
fun DetailItem(label: String, value: String) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

fun onEditClicked() {


}

fun onDeleteClicked() {

}

@Composable
fun DeleteDialog(onDismiss: () -> Unit, onDelete: () -> Unit, modifier: Modifier = Modifier) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Are you sure you want to delete this yarn?",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(16.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onDelete()
                        },
                        modifier = modifier.weight(1f)
                    ) {
                        Text("Delete")
                    }
                    Spacer(modifier.width(8.dp))
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        modifier = modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}