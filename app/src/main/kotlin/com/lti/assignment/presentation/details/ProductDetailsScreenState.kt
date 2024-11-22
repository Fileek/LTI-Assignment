package com.lti.assignment.presentation.details

import com.lti.assignment.presentation.ProductModel

data class ProductDetailsScreenState(
    val productModel: ProductModel? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
