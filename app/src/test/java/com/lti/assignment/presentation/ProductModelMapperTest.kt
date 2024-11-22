package com.lti.assignment.presentation

import com.lti.assignment.domain.Product
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductModelMapperTest {

    private lateinit var mapper: ProductModelMapper

    @Before
    fun setUp() {
        mapper = ProductModelMapper()
    }

    @Test
    fun `mapToProductModel maps Product to ProductModel correctly`() {
        val product = Product(
            id = 1,
            name = "Test Product",
            description = "Test Description",
            price = 9.99,
            rating = 4.5f
        )

        val expectedProductModel = ProductModel(
            id = 1,
            name = "Test Product",
            description = "Test Description",
            price = 9.99,
            rating = 4.5f
        )

        val productModel = mapper.mapToProductModel(product)

        assertEquals(expectedProductModel, productModel)
    }

    @Test
    fun `mapToProduct maps ProductModel to Product correctly`() {
        val productModel = ProductModel(
            id = 1,
            name = "Test Product",
            description = "Test Description",
            price = 9.99,
            rating = 4.5f
        )

        val expectedProduct = Product(
            id = 1,
            name = "Test Product",
            description = "Test Description",
            price = 9.99,
            rating = 4.5f
        )

        val product = mapper.mapToProduct(productModel)

        assertEquals(expectedProduct, product)
    }
}