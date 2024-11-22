package com.lti.assignment.domain

data class Product(
    val name: String,
    val description: String,
    val price: Double,
    val rating: Float = 0.0f,
    val id: Int = 0,
)
