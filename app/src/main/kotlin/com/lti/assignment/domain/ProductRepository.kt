package com.lti.assignment.domain

import com.lti.assignment.utils.ProductResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getAllProducts(): Flow<ProductResult<List<Product>>>

    fun getProductById(productId: Int): Flow<ProductResult<Product>>

    suspend fun addProduct(product: Product)

    suspend fun deleteProduct(productId: Int)
}