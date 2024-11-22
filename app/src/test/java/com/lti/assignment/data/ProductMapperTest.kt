package com.lti.assignment.data

import com.lti.assignment.domain.Product
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductMapperTest {

    private lateinit var mapper: ProductMapper

    @Before
    fun setUp() {
        mapper = ProductMapper()
    }

    @Test
    fun `mapToProductEntity maps Product to ProductEntity correctly`() {
        val product = Product(
            id = 1,
            name = "Test Product",
            description = "Test Description",
            price = 9.99
        )

        val expectedProductEntity = ProductEntity(
            id = 1,
            name = "Test Product",
            description = "Test Description",
            price = 9.99
        )

        val productEntity = mapper.mapToProductEntity(product)

        assertEquals(expectedProductEntity, productEntity)
    }

    @Test
    fun `mapToProduct maps ProductEntity to Product correctly`() {
        val productEntity = ProductEntity(
            id = 1,
            name = "Test Product",
            description = "Test Description",
            price = 9.99
        )

        val expectedProduct = Product(
            id = 1,
            name = "Test Product",
            description = "Test Description",
            price = 9.99
        )

        val product = mapper.mapToProduct(productEntity)

        assertEquals(expectedProduct, product)
    }
}