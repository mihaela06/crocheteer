package com.crocheteer.crocheteer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.crocheteer.crocheteer.data.entities.YarnType
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors

@Composable
fun YarnMainInfo(yarnType: YarnType, modifier: Modifier = Modifier) {

    Row (
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ){
        yarnType.genericPhotoUrl?.let {
            DisplayImageFromUrl(
                imageUrl = it,
                modifier = modifier
                    .size(100.dp)
                    .padding(6.dp)
                    .background(Color.Transparent)
            )
        }
        Column(
            modifier = modifier
                .weight(1f)
                .padding(16.dp)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = yarnType.companyName)
            Spacer(modifier = modifier.height(5.dp))
            Text(text = yarnType.name)
            Spacer(modifier = modifier.height(5.dp))
            yarnType.weight?.let { Text(text = it.name) }
        }
    }
}
