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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crocheteer.crocheteer.R
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import com.crocheteer.crocheteer.ui.components.DisplayImageFromUrl
import com.crocheteer.crocheteer.ui.components.NavigateBackIcon
import com.crocheteer.crocheteer.ui.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnDetailsScreen(onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
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
                    IconButton(onClick = { onDeleteClicked() }) {
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
                        modifier = modifier.weight(1f).size(150.dp)
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
}


@Composable
fun DetailItem(label: String, value: String) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp), // Rounded corners
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Elevation for a subtle shadow
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant) // Use MaterialTheme for color scheme
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

fun onEditClicked() {



}

fun onDeleteClicked() {

}