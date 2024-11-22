package com.lti.assignment.presentation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Products : Route

    @Serializable
    data class ProductDetails(val productID: Int = 0) : Route

    @Serializable
    data object AddProduct : Route
}