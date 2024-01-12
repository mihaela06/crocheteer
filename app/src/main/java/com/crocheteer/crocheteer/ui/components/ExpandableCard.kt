package com.crocheteer.crocheteer.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.crocheteer.crocheteer.R
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import com.crocheteer.crocheteer.navigation.Screens
import com.crocheteer.crocheteer.ui.theme.Shapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(yarnTypeWithColors: YarnTypeWithColors, modifier: Modifier = Modifier, onNavigate: () -> Unit) {

    var expandableState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandableState) 180f else 0f, label = ""
    )
    val showColorDialog = remember { mutableStateOf(false) }
    val imageUri = remember { mutableStateOf("") }
    val colorCode = remember { mutableStateOf("") }
    val colorName = remember { mutableStateOf("") }
    val colorQuantity = remember { mutableStateOf("") }


    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = Shapes.medium,
        onClick = {
            expandableState = !expandableState
        },


        ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Box() {
                    Row {
                        yarnTypeWithColors.type.genericPhotoUrl?.let {
                            DisplayImageFromUrl(
                                imageUrl = it,
                                modifier = modifier.weight(1f)
                            )
                        }
                        Column(
                            modifier = modifier
                                .weight(2f)
                                .padding(5.dp)
                                .align(Alignment.CenterVertically),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = yarnTypeWithColors.type.companyName)
                            Spacer(modifier = modifier.height(5.dp))
                            Text(text = yarnTypeWithColors.type.name)
                            Spacer(modifier = modifier.height(5.dp))
                            yarnTypeWithColors.type.weight?.let { Text(text = it.name) }
                        }
                        Spacer(modifier = modifier.height(15.dp))
                        IconButton(
                            modifier = modifier
                                .alpha(0.5f)
                                .weight(1f),
                            onClick = { onNavigate() }) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Yarn Details"
                            )
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier
                        .weight(7f)
                        .padding(5.dp),
                    text = "${if (yarnTypeWithColors.colors == null) "0" else yarnTypeWithColors.colors.size.toString()} colors in stash"
                )
                IconButton(
                    modifier = modifier
                        .alpha(0.5f)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = { expandableState = !expandableState }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
                IconButton(
                    modifier = modifier
                        .alpha(0.5f)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = { showColorDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Button"
                    )
                }
            }

            if (expandableState) {
                Column {
                    yarnTypeWithColors.colors?.forEach {
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
                                it.colorCode?.let { it1 ->
                                    Text(
                                        text = it1,
                                        modifier = modifier.padding(start = 5.dp)
                                    )
                                }
                                Spacer(modifier = modifier.width(8.dp))
                                Text(text = "-")
                                Spacer(modifier = modifier.width(8.dp))
                                Text(text = it.colorName)
                            }
                            Spacer(modifier = modifier.weight(1f))
                            Text(text = "${it.quantity} g")
                        }
                    }
                }

            }

            if (showColorDialog.value) {
                Dialog(onDismissRequest = { showColorDialog.value = false }) {
                    Column(modifier = modifier.padding(16.dp)) {
                        TextField(
                            value = imageUri.value,
                            onValueChange = { imageUri.value = it },
                            label = { Text("Yarn Color Image URI") })

                        TextField(
                            value = colorCode.value,
                            onValueChange = { colorCode.value = it },
                            label = { Text("Color Code") }
                        )
                        TextField(
                            value = colorName.value,
                            onValueChange = { colorName.value = it },
                            label = { Text("Color Name") }
                        )
                        TextField(
                            value = colorQuantity.value,
                            onValueChange = { colorQuantity.value = it },
                            label = { Text("Color Quantity") }
                        )
                        Row (
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Button(onClick = {
                                showColorDialog.value = false
                                //add the data to database
                            }) {
                                Text("Add to List")
                            }
                            Spacer(modifier.weight(0.5f))
                            Button(onClick = {
                                showColorDialog.value = false
                            }) {
                                Text("Cancel")
                            }
                        }

                    }

                }
            }

        }
    }
}


@Composable
fun DisplayImageFromUrl(imageUrl: String, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(model = imageUrl)

    Image(
        painter = painter,
        contentDescription = "Loaded image from url",
        modifier = modifier
    )
}

