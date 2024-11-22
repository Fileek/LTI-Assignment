package com.lti.assignment.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lti.assignment.R
import com.lti.assignment.presentation.foundation.ErrorContent
import com.lti.assignment.presentation.foundation.LoadingContent
import com.lti.assignment.presentation.ProductModel
import com.lti.assignment.theme.LTIAssignmentTheme
import com.lti.assignment.utils.LocalNavController

@Composable
fun ProductDetailsScreen() {
    val navController = LocalNavController.current
    val viewModel: ProductDetailsViewModel = hiltViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    ProductDetailsScreenContent(
        state = state,
        onBack = navController::popBackStack,
        onRetry = viewModel::retry
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProductDetailsScreenContent(
    state: ProductDetailsScreenState,
    onBack: () -> Unit,
    onRetry: () -> Unit = {}
) {
    val product = state.productModel

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when {
                            state.isLoading -> stringResource(R.string.loading)
                            product == null -> stringResource(R.string.error)
                            else -> product.name
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when {
            state.isLoading -> {
                LoadingContent(modifier = Modifier.padding(innerPadding))
            }

            state.isError -> {
                ErrorContent(
                    modifier = Modifier.padding(innerPadding),
                    onRetry = onRetry
                )
            }

            product != null -> {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(32.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Text(text = product.description, fontSize = 22.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${stringResource(R.string.rating)} ${product.rating}",
                            fontSize = 28.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.star),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${stringResource(R.string.price)} ${product.formattedPrice}",
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductDetailsScreenPreview() {
    LTIAssignmentTheme {
        ProductDetailsScreenContent(
            state = ProductDetailsScreenState(
                ProductModel(
                    name = "Product name",
                    description = "Product description",
                    rating = 5.0f,
                    price = 100.0
                )
            ),
            onBack = {}
        )
    }
}

@Preview
@Composable
private fun ProductDetailsScreenLoadingPreview() {
    LTIAssignmentTheme {
        ProductDetailsScreenContent(
            state = ProductDetailsScreenState(isLoading = true),
            onBack = {}
        )
    }
}

@Preview
@Composable
private fun ProductDetailsScreenErrorPreview() {
    LTIAssignmentTheme {
        ProductDetailsScreenContent(
            state = ProductDetailsScreenState(isError = true),
            onBack = {}
        )
    }
}