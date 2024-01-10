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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crocheteer.crocheteer.R
import com.crocheteer.crocheteer.ui.theme.Shapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard() {

    var expandableState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandableState) 180f else 0f, label = ""
    )

    Card(
        modifier = Modifier
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box() {
                    Row {
                        Image(
                            painter = painterResource(id = R.mipmap.logo),
                            contentDescription = "SplashScreenLogo",
                            modifier = Modifier.weight(1f)
                        )
                        Column(
                            modifier = Modifier
                                .weight(2f)
                                .padding(5.dp)
                                .align(Alignment.CenterVertically),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "text2")
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = "text3")
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = "text4")
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(7f)
                        .padding(5.dp),
                    text = "text5"
                )
                IconButton(
                    modifier = Modifier
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
                    modifier = Modifier
                        .alpha(0.5f)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Button"
                    )
                }
            }

            if (expandableState) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.logo),
                        contentDescription = "SplashScreenLogo",
                        modifier = Modifier.size(width = 50.dp, height = 50.dp)
                    )
                    // Container for text 9 and 10 to keep them together
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "text9",
                            modifier = Modifier.padding(start = 5.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "-")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "text10")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "text11")
                }
            }

        }


    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableCardPreview() {
    ExpandableCard()
}

