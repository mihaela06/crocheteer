package com.crocheteer.crocheteer.screens

import android.annotation.SuppressLint
import android.content.res.Resources
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.crocheteer.crocheteer.R
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import com.crocheteer.crocheteer.ui.components.DisplayImageFromUrl
import com.crocheteer.crocheteer.ui.components.NavigateBackIcon
import com.crocheteer.crocheteer.ui.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnDetailsScreen(onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {

    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(text = "Yarn Details",
                navigationIcon = {
                    NavigateBackIcon(
                        onNavigateBack, "Go back to yarn stash"
                    )
                },
                actions = {
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
            Column(
                modifier = modifier
                    //.padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row {
//                        yarnTypeWithColors.type.genericPhotoUrl?.let {
//                            DisplayImageFromUrl(
//                                imageUrl = it,
//                                modifier = modifier.weight(1f)
//                            )
                    Image(
                        painter = painterResource(id = R.mipmap.logo),
                        contentDescription = "SplashScreenLogo",
                        modifier = modifier
                            .weight(1f)
                            .size(150.dp)
                    )
                    Column(
                        modifier = modifier
                            .weight(1f)
                            .padding(5.dp)
                            .align(Alignment.CenterVertically),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        //Text(text = yarnTypeWithColors.type.companyName)
                        Text(text = "companyName", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = modifier.height(16.dp))
                        //Text(text = yarnTypeWithColors.type.name)
                        Text(text = "Name", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = modifier.height(16.dp))
                        Text(text = "Weight", style = MaterialTheme.typography.bodyLarge)
                        //yarnTypeWithColors.type.weight?.let { Text(text = it.name) }
                    }
                    Spacer(modifier = modifier.height(15.dp))
                }

                Divider(modifier = modifier.padding(top = 16.dp, bottom = 16.dp))

                DetailItem(label = "Colors in stash", value = "yarn.colorsInStash")
                DetailItem(label = "Length", value = "yarn.length meters")
                DetailItem(label = "Weight", value = "yarn.weight grams")
                DetailItem(label = "Machine Washable", value = "Yes")

                Divider(modifier = modifier.padding(top = 16.dp, bottom = 16.dp))

                Column {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = R.mipmap.logo),
                            contentDescription = "SplashScreenLogo",
                            modifier = modifier.size(width = 50.dp, height = 50.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            //it.colorCode?.let { it1 ->
                            Text(
                                text = "Color code",
                                modifier = modifier.padding(start = 5.dp)
                            )
                            //}
                            Spacer(modifier = modifier.width(8.dp))
                            Text(text = "-")
                            Spacer(modifier = modifier.width(8.dp))
                            Text(text = "colorName")
                        }
                        Spacer(modifier = modifier.weight(1f))
                        Text(text = "quantity g")
                    }
                }
            }


        }

    }

    if (showDeleteDialog) {
        DeleteDialog(
            onDismiss = { showDeleteDialog = false },
            onDelete = { showDeleteDialog = false })
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