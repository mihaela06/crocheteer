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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddYarnColor(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onAddToList: () -> Unit,
    imageUri: String,
    colorCode: String,
    colorName: String,
    colorQuantity: String
    ) {
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
                Text("Add New Yarn Color", style = MaterialTheme.typography.headlineSmall)
                OutlinedTextField(
                    value = imageUri,
                    onValueChange = {},
                    label = { Text("Yarn Color Image URI") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = colorCode,
                    onValueChange = {},
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
                    onValueChange = {},
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
                    onValueChange = {},
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
                            onAddToList()
                            //add the data to database
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