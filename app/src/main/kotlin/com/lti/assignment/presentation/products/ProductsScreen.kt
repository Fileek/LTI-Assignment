package com.lti.assignment.presentation.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lti.assignment.R
import com.lti.assignment.presentation.foundation.ConfirmationDialog
import com.lti.assignment.presentation.foundation.ErrorContent
import com.lti.assignment.presentation.foundation.LoadingContent
import com.lti.assignment.presentation.ProductItem
import com.lti.assignment.presentation.ProductModel
import com.lti.assignment.presentation.Route
import com.lti.assignment.theme.LTIAssignmentTheme
import com.lti.assignment.utils.LocalNavController
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ProductsScreen() {
    val viewModel: ProductsViewModel = hiltViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    ProductsScreenContent(
        state = state,
        onNavigate = navController::navigate,
        onDeleteProduct = viewModel::deleteProduct,
        onRetry = viewModel::retry
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProductsScreenContent(
    state: ProductsScreenState,
    onNavigate: (Route) -> Unit,
    onDeleteProduct: (Int) -> Unit,
    onRetry: () -> Unit = {}
) {
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var productIDToDelete by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.product_list)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigate(Route.AddProduct)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_product)
                )
            }
        }
    ) { innerPadding ->
        when {
            state.isLoading -> {
                LoadingContent(modifier = Modifier.padding(innerPadding))
            }

            state.isError -> {
                ErrorContent(modifier = Modifier.padding(innerPadding), onRetry = onRetry)
            }

            state.products != null -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.products) { product ->
                        ProductItem(
                            product = product,
                            onDelete = {
                                productIDToDelete = product.id
                                showConfirmationDialog = true
                            },
                            onClick = {
                                onNavigate(Route.ProductDetails(product.id))
                            }
                        )
                    }
                }
            }
        }

        if (showConfirmationDialog) {
            ConfirmationDialog(
                title = stringResource(R.string.confirm_to_delete),
                onDismiss = { showConfirmationDialog = false },
                onConfirm = {
                    onDeleteProduct(productIDToDelete)
                    showConfirmationDialog = false
                }
            )
        }
    }
}

@Preview
@Composable
private fun ProductsScreenContentPreview() {
    LTIAssignmentTheme {
        ProductsScreenContent(
            state = ProductsScreenState(
                products = List(5) { index ->
                    ProductModel(
                        name = "Product $index",
                        description = "Description",
                        price = 100.0 * index
                    )
                }.toImmutableList()
            ),
            onNavigate = {},
            onDeleteProduct = {}
        )
    }
}

@Preview
@Composable
private fun ProductsScreenContentLoadingPreview() {
    LTIAssignmentTheme {
        ProductsScreenContent(
            state = ProductsScreenState(isLoading = true),
            onNavigate = {},
            onDeleteProduct = {}
        )
    }
}

@Preview
@Composable
private fun ProductsScreenContentErrorPreview() {
    LTIAssignmentTheme {
        ProductsScreenContent(
            state = ProductsScreenState(isError = true),
            onNavigate = {},
            onDeleteProduct = {}
        )
    }
}

