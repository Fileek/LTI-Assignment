package com.lti.assignment.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lti.assignment.domain.ProductRepository
import com.lti.assignment.presentation.ProductModelMapper
import com.lti.assignment.presentation.Route
import com.lti.assignment.utils.ProductResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val productModelMapper: ProductModelMapper,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(ProductDetailsScreenState())
    val stateFlow: StateFlow<ProductDetailsScreenState> = _stateFlow

    init {
        viewModelScope.launch {
            fetchProduct()
        }
    }

    private suspend fun fetchProduct() {
        val productID = savedStateHandle.toRoute<Route.ProductDetails>().productID

        repository.getProductById(productID).collectLatest { productResult ->
            when (productResult) {
                is ProductResult.Success -> {
                    _stateFlow.update {
                        it.copy(
                            productModel = productModelMapper.mapToProductModel(productResult.data),
                            isError = false,
                            isLoading = false
                        )
                    }
                }

                is ProductResult.Error -> {
                    _stateFlow.update { it.copy(isError = true) }
                }

                is ProductResult.Loading -> {
                    _stateFlow.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun retry() {
        viewModelScope.launch {
            fetchProduct()
        }
    }
}