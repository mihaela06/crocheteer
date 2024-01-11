package com.crocheteer.crocheteer.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingActionButton(modifier: Modifier = Modifier){
    val showYarnDialog = remember { mutableStateOf(false) }

    androidx.compose.material3.FloatingActionButton(
        onClick = { showYarnDialog.value = true },
        modifier = modifier.padding(16.dp),
        content = {
            Icon(Icons.Filled.Add, contentDescription = "Add Yarn")
        }
    )

    if (showYarnDialog.value){
        Dialog(onDismissRequest = { showYarnDialog.value = false }) {
            val yarnPhotoUrl = remember{ mutableStateOf("") }
            val yarnCompanyName = remember{ mutableStateOf("") }
            val yarnName = remember{ mutableStateOf("") }
            val yarnWeight = remember{ mutableStateOf("") }
            val yarnMachineWashable = remember{ mutableStateOf("") }
            val yarnGrams = remember{ mutableStateOf("") }
            val yarnLength = remember{ mutableStateOf("") }


            Column(modifier = modifier.padding(16.dp)) {
                Text(text = "Add a New Yarn")

                TextField(
                    value = yarnPhotoUrl.value,
                    onValueChange = { yarnPhotoUrl.value = it },
                    label = { Text("Yarn Photo URL") }
                )
                TextField(
                    value = yarnCompanyName.value,
                    onValueChange = { yarnCompanyName.value = it },
                    label = { Text("Yarn Company Name") }
                )
                TextField(
                    value = yarnName.value,
                    onValueChange = { yarnName.value = it },
                    label = { Text("Yarn Name") }
                )
                TextField(
                    value = yarnWeight.value,
                    onValueChange = { yarnWeight.value = it },
                    label = { Text("Yarn Weight") }
                )
                TextField(
                    value = yarnMachineWashable.value,
                    onValueChange = { yarnMachineWashable.value = it },
                    label = { Text("Yarn Machine Washable") }
                )
                TextField(
                    value = yarnGrams.value,
                    onValueChange = { yarnGrams.value = it },
                    label = { Text("Yarn Grams") }
                )
                TextField(
                    value = yarnLength.value,
                    onValueChange = { yarnLength.value = it },
                    label = { Text("Yarn Length") }
                )

                Row {
                    Button(onClick = { showYarnDialog.value = false }) {
                        Text("Cancel")
                    }

                    Spacer(Modifier.weight(1f))

                    Button(onClick = {
                        // Add yarn to database
                        showYarnDialog.value = false
                    }) {
                        Text("Add to List")
                    }
                }
            }
        }
    }
}