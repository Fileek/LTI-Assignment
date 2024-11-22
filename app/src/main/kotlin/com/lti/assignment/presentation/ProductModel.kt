package com.lti.assignment.presentation

import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
data class ProductModel(
    val name: String,
    val description: String,
    val price: Double,
    val rating: Float = 0f,
    val id: Int = 0,
) {
    val formattedPrice: String
        get() = String.format(Locale.getDefault(), "%.2f$", price)
}
