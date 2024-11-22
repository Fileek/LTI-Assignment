package com.lti.assignment.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lti.assignment.domain.ProductRepository
import com.lti.assignment.presentation.ProductModelMapper
import com.lti.assignment.utils.ProductResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val productModelMapper: ProductModelMapper
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(ProductsScreenState())
    val stateFlow: StateFlow<ProductsScreenState> = _stateFlow

    init {
        viewModelScope.launch { fetchProducts() }
    }

    private suspend fun fetchProducts() {
        repository.getAllProducts().collectLatest { productResult ->
            when (productResult) {
                is ProductResult.Success -> {
                    _stateFlow.update {
                        it.copy(
                            products = productResult.data.map { product ->
                                productModelMapper.mapToProductModel(product)
                            }.toImmutableList(),
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

    fun deleteProduct(productId: Int) {
        viewModelScope.launch { repository.deleteProduct(productId) }
    }

    fun retry() {
        viewModelScope.launch { fetchProducts() }
    }
}