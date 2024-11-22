package com.lti.assignment.presentation.addproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lti.assignment.domain.ProductRepository
import com.lti.assignment.presentation.ProductModel
import com.lti.assignment.presentation.ProductModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val productModelMapper: ProductModelMapper
) : ViewModel() {

    fun addProduct(product: ProductModel) {
        viewModelScope.launch {
            repository.addProduct(
                productModelMapper.mapToProduct(product)
            )
        }
    }
}