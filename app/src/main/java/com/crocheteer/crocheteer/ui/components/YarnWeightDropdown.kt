package com.crocheteer.crocheteer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crocheteer.crocheteer.data.entities.YarnWeight

@Composable
fun YarnWeightDropdown(
    weight: YarnWeight,
    onWeightChange: (YarnWeight) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val items = YarnWeight.entries.toTypedArray()
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        Text(
            weight.prettifiedName,
            modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = true })
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier.height(200.dp)
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    onClick = {
                        onWeightChange(items[index])
                        expanded = false
                    },
                    text = { Text(text = s.prettifiedName) },
                    modifier = modifier
                )
            }
        }
    }
}

@Preview
@Composable
fun YarnWeightPreview() {
    var weight by remember {
        mutableStateOf(YarnWeight.Aran)
    }
    YarnWeightDropdown(weight = weight, onWeightChange = { weight = it })
}
