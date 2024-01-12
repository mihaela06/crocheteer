package com.crocheteer.crocheteer.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.crocheteer.crocheteer.R

@Composable
fun ImagePicker(onUriChange: (Uri) -> Unit) {
    var imageUri: Any? by remember { mutableStateOf(R.drawable.add_image) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            imageUri = it
            onUriChange(it)
        }
    }

    AsyncImage(
        modifier = Modifier
            .size(200.dp)
            .clickable {
                photoPicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
        model = ImageRequest.Builder(LocalContext.current).data(imageUri)
            .crossfade(enable = true).build(),
        contentDescription = "Yarn color",
        contentScale = ContentScale.Crop,
    )
}

@Preview
@Composable
fun ImagePickerPreview() {
    ImagePicker(onUriChange = {})
}
