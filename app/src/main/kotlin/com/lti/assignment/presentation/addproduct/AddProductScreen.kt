package com.lti.assignment.presentation.addproduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lti.assignment.R
import com.lti.assignment.presentation.foundation.ButtonsRow
import com.lti.assignment.presentation.foundation.ConfirmationDialog
import com.lti.assignment.presentation.ProductModel
import com.lti.assignment.theme.LTIAssignmentTheme
import com.lti.assignment.utils.LocalNavController

@Composable
fun AddProductScreen() {
    val viewModel: AddProductViewModel = hiltViewModel()
    val navController = LocalNavController.current

    AddProductScreenContent(
        onBack = navController::popBackStack,
        onAddProduct = viewModel::addProduct
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddProductScreenContent(
    onBack: () -> Unit,
    onAddProduct: (ProductModel) -> Unit
) {
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var hasNameError by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var hasDescriptionError by remember { mutableStateOf(false) }
    var rating by remember { mutableStateOf("") }
    var hasRatingError by remember { mutableStateOf(false) }
    var price by remember { mutableStateOf("") }
    var hasPriceError by remember { mutableStateOf(false) }

    fun validateFields(): Boolean {
        hasNameError = name.isBlank()
        hasDescriptionError = description.isBlank()
        hasRatingError = rating.isBlank()
        hasPriceError = price.isBlank()
        return !hasNameError && !hasDescriptionError && !hasRatingError && !hasPriceError
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_product)) },
                navigationIcon = {
                    IconButton(
                        onClick = { showConfirmationDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                value = name,
                onValueChange = {
                    name = it
                    hasNameError = false
                },
                isError = hasNameError,
                label = stringResource(R.string.product_name)
            )
            InputField(
                value = description,
                onValueChange = {
                    description = it
                    hasDescriptionError = false
                },
                isError = hasDescriptionError,
                label = stringResource(R.string.product_description)
            )
            InputField(
                value = rating,
                onValueChange = {
                    rating = it
                    hasRatingError = false
                },
                isError = hasRatingError,
                label = stringResource(R.string.product_rating),
                onlyNumber = true
            )
            InputField(
                value = price,
                onValueChange = {
                    price = it
                    hasPriceError = false
                },
                isError = hasPriceError,
                label = stringResource(R.string.product_price),
                onlyNumber = true
            )
            Spacer(modifier = Modifier.weight(1f))
            ButtonsRow(
                primaryLabel = stringResource(R.string.save),
                onPrimaryClick = {
                    if (validateFields()) {
                        onAddProduct(
                            ProductModel(
                                name = name,
                                description = description,
                                rating = rating.toFloatOrNull() ?: 0f,
                                price = price.toDoubleOrNull() ?: 0.0
                            )
                        )
                        onBack()
                    }
                },
                secondaryLabel = stringResource(R.string.cancel),
                onSecondaryClick = { showConfirmationDialog = true },
            )
        }
    }

    if (showConfirmationDialog) {
        ConfirmationDialog(
            title = stringResource(R.string.confirm_to_discard),
            onDismiss = { showConfirmationDialog = false },
            onConfirm = {
                showConfirmationDialog = false
                onBack()
            }
        )
    }
}

@Composable
private fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    label: String,
    onlyNumber: Boolean = false,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = {
            when {
                onlyNumber && it.toFloatOrNull() != null -> onValueChange(it)
                !onlyNumber -> onValueChange(it)
            }
        },
        isError = isError,
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = if (onlyNumber) KeyboardType.Number else KeyboardType.Text
        )
    )
}

@Preview
@Composable
private fun AddProductScreenPreview() {
    LTIAssignmentTheme {
        AddProductScreenContent(onBack = {}, onAddProduct = {})
    }
}