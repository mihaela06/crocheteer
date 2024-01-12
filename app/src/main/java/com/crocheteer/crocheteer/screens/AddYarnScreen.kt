package com.crocheteer.crocheteer.screens

import androidx.compose.material3.Switch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crocheteer.crocheteer.data.entities.YarnType
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import com.crocheteer.crocheteer.data.entities.YarnWeight
import com.crocheteer.crocheteer.ui.components.AutoCompleteTextField
import com.crocheteer.crocheteer.ui.components.NavigateBackIcon
import com.crocheteer.crocheteer.ui.components.RavelrySearchDialog
import com.crocheteer.crocheteer.ui.components.TopBar
import com.crocheteer.crocheteer.ui.components.YarnWeightDropdown
import com.crocheteer.crocheteer.ui.viewmodels.AddYarnViewModel
import kotlinx.coroutines.launch
import java.lang.Integer.parseInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddYarnScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddYarnViewModel = hiltViewModel<AddYarnViewModel>(),
) {
    val scope = rememberCoroutineScope()

    var suggestions by remember { mutableStateOf(listOf<String>()) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(
                text = "Stash a new yarn",
                navigationIcon = {
                    NavigateBackIcon(
                        onNavigateBack, "Go back to yarn stash"
                    )
                })
        }) { padding ->
        Box(
            modifier = modifier
                .padding(padding)
                .background(Color.Transparent),
        ) {
            AddYarn(
                stashYarn = {
                    scope.launch { viewModel.stashYarn(it) }
                    onNavigateBack()
                },
                onGetCompanySuggestions = { searchTerm ->
                    scope.launch { suggestions = viewModel.getCompanyNames(searchTerm) }
                },
                companySuggestions = suggestions,
                modifier
            )
        }
    }
}

@Composable
fun AddYarn(
    stashYarn: (YarnTypeWithColors) -> Unit,
    onGetCompanySuggestions: (String) -> Unit,
    companySuggestions: List<String>,
    modifier: Modifier = Modifier
) {
    var isDialogVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .fillMaxWidth()) {
        Button(
            onClick = { isDialogVisible = true },
            modifier = modifier.fillMaxWidth()
        ) {
            Text("Search on Ravelry")
        }
        Spacer(modifier = modifier.height(16.dp))
        AddYarnForm(
            onAddClick = {
                stashYarn(it)
            },
            companySuggestions = companySuggestions,
            onGetCompanySuggestions = onGetCompanySuggestions,
            isDialogVisible = isDialogVisible,
            onDialogClose = { isDialogVisible = false },
            modifier = modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddYarnForm(
    onAddClick: (YarnTypeWithColors) -> Unit,
    companySuggestions: List<String>,
    onGetCompanySuggestions: (String) -> Unit,
    isDialogVisible: Boolean,
    onDialogClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var yarn by remember {
        mutableStateOf(
            YarnType(
                companyName = "",
                name = "",
                genericPhotoUrl = "",
                grams = 0,
                length = 0,
                machineWashable = false,
                weight = YarnWeight.Aran,
            )
        )
    }
    var isValid by remember {
        mutableStateOf(false)
    }

    var yarnPhotoUrl by remember { mutableStateOf("") }
    var yarnCompanyName by remember { mutableStateOf("") }
    var yarnName by remember { mutableStateOf("") }
    var yarnWeight by remember { mutableStateOf(YarnWeight.Aran) }
    var yarnIsMachineWashable by remember { mutableStateOf(false) }
    var yarnGrams by remember { mutableStateOf("") }
    var yarnLength by remember { mutableStateOf("") }

    try {
        val parsedYarnType = YarnType(
            name = yarnName,
            companyName = yarnCompanyName,
            grams = parseInt(yarnGrams),
            length = parseInt(yarnLength),
            machineWashable = yarnIsMachineWashable,
            weight = yarnWeight,
            genericPhotoUrl = yarnPhotoUrl.ifEmpty { null }
        )
        if (yarnName.isEmpty()) throw Exception()
        if (yarnCompanyName.isEmpty()) throw Exception()
        if (parseInt(yarnGrams) < 1) throw Exception()
        if (parseInt(yarnLength) < 1) throw Exception()

        isValid = true
        yarn = parsedYarnType
    } catch (_: Exception) {
        isValid = false
    }

    Column(modifier = modifier.padding(6.dp)) {
        AutoCompleteTextField(
            text = yarnCompanyName,
            onTextChange = {
                yarnCompanyName = it
                onGetCompanySuggestions(it)
            },
            suggestions = companySuggestions,
            modifier = modifier.fillMaxWidth(),
            label = "Company name"
        )
        Spacer(modifier.height(16.dp))
        OutlinedTextField(
            value = yarnName,
            onValueChange = { yarnName = it },
            label = { Text("Yarn name") },
            isError = yarnName.isEmpty(),
            modifier = modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier.height(16.dp))
        YarnWeightDropdown(weight = yarnWeight, onWeightChange = { yarnWeight = it })
        Spacer(modifier.height(16.dp))
        OutlinedTextField(
            value = yarnGrams,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { yarnGrams = it },
            isError = yarnGrams.toIntOrNull() == null || (yarnGrams.toInt() < 1),
            label = { Text("Grams per skein") },
            modifier = modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier.height(16.dp))
        OutlinedTextField(
            value = yarnLength,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { yarnLength = it },
            isError = yarnLength.toIntOrNull() == null || (yarnLength.toInt() < 1),
            label = { Text("Meters per skein") },
            modifier = modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier.height(16.dp))
        OutlinedTextField(
            value = yarnPhotoUrl,
            onValueChange = { yarnPhotoUrl = it },
            label = { Text("Photo URL (optional)") },
            modifier = modifier.fillMaxWidth(),
            singleLine = true
        )
        // TODO after valid URL is entered, render the image
        Spacer(modifier.height(16.dp))
        Row(modifier = modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Is machine washable?",
                modifier = modifier,
                )
//            Checkbox(
//                checked = yarnIsMachineWashable,
//                onCheckedChange = { yarnIsMachineWashable = !yarnIsMachineWashable },
//            )
            Switch(
                checked = yarnIsMachineWashable,
                onCheckedChange = { yarnIsMachineWashable = it },
            )
//            Spacer(modifier = modifier)
        }
        Spacer(modifier.height(16.dp))
        Button(
            modifier = modifier.align(Alignment.CenterHorizontally),
            enabled = isValid,
            shape = RoundedCornerShape(50),
            onClick = {
                onAddClick(YarnTypeWithColors(yarn, listOf()))
            }) {
            Text("Add to stash")
        }
    }
    if (isDialogVisible) RavelrySearchDialog(
        onDismissRequest = { onDialogClose() },
        onConfirmation = {
            yarnCompanyName = it.companyName
            yarnName = it.name
            yarnGrams = it.grams.toString()
            yarnLength = it.length.toString()
            yarnIsMachineWashable = it.machineWashable ?: false
            yarnPhotoUrl = it.genericPhotoUrl ?: ""
            yarnWeight = it.weight ?: YarnWeight.Aran
            onDialogClose()
        }
    )
}
