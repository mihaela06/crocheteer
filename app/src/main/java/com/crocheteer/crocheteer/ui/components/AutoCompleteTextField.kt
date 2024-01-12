package com.crocheteer.crocheteer.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextField(
    text: String,
    onTextChange: (String) -> Unit,
    suggestions: List<String>,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = modifier.fillMaxWidth()) {
            TextField(modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
                value = text,
                onValueChange = {
                    onTextChange(it)
                    expanded = true
                },
                placeholder = {
                    placeholder?.let { Text(it) }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                label = {
                    label?.let { Text(it) }
                },
                isError = text.isEmpty(),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            modifier = modifier.size(24.dp),
                            imageVector =
                            if (expanded) Icons.Rounded.KeyboardArrowUp
                            else Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "Toggle suggestions"
                        )
                    }
                })
        }
        AnimatedVisibility(visible = expanded) {
            Card(
                modifier = modifier
                    .padding(horizontal = 5.dp)
                    .width(textFieldSize.width.dp)
            ) {
                LazyColumn(
                    modifier = modifier.heightIn(max = 150.dp),
                ) {
                    itemsIndexed(suggestions) { _, it ->
                        SuggestedText(title = it) { title ->
                            onTextChange(title)
                            expanded = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SuggestedText(
    title: String,
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable {
            onSelect(title)
        }
        .padding(10.dp)) {
        Text(text = title, fontSize = 16.sp)
    }
}

@Preview
@Composable
fun AutoCompletePreview() {
    val companies = listOf(
        "Catania",
        "Super Saver"
    )

    var text by remember { mutableStateOf("") }

    AutoCompleteTextField(
        text = text,
        onTextChange = { text = it },
        suggestions = companies.filter {
            if (text.isEmpty()) true else it.lowercase().contains(text.lowercase())
        },
        placeholder = "Enter something",
        label = null
    )
}