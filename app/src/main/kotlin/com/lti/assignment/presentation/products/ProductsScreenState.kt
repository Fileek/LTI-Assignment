package com.lti.assignment.presentation.products

import com.lti.assignment.presentation.ProductModel
import kotlinx.collections.immutable.ImmutableList

data class ProductsScreenState(
    val products: ImmutableList<ProductModel>? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false
)
