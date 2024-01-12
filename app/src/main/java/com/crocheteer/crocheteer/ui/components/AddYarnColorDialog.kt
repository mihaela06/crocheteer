package com.crocheteer.crocheteer.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.crocheteer.crocheteer.data.entities.YarnColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddYarnColor(
    yarnTypeId: Long,
    onDismiss: () -> Unit,
    onAddToList: (YarnColor) -> Unit,
    modifier: Modifier = Modifier,
) {
    var imageUri by remember { mutableStateOf("") }
    var colorCode by remember { mutableStateOf("") }
    var colorName by remember { mutableStateOf("") }
    var colorQuantity by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImagePicker(onUriChange = { imageUri = it.toString() })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = colorCode,
                    onValueChange = { colorCode = it },
                    label = { Text("Color Code") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Create,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = colorName,
                    onValueChange = { colorName = it },
                    label = { Text("Color Name") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Create,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = colorQuantity,
                    onValueChange = { colorQuantity = it },
                    label = { Text("Color Quantity") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Create,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onAddToList(
                                YarnColor(
                                    yarnTypeId = yarnTypeId,
                                    quantity = colorQuantity.toIntOrNull() ?: 0,
                                    colorName = colorName,
                                    colorCode = colorCode,
                                    photoUri = imageUri
                                )
                            )
                        },
                        modifier = modifier.weight(1f)
                    ) {
                        Text("Add to List")
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