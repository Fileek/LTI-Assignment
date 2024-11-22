package com.lti.assignment.data

import com.lti.assignment.domain.Product
import com.lti.assignment.domain.ProductRepository
import com.lti.assignment.utils.ProductResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val productDAO: ProductDAO,
    private val productMapper: ProductMapper
) : ProductRepository {

    override fun getAllProducts(): Flow<ProductResult<List<Product>>> {
        return productDAO.getAllProducts()
            .map { productEntities ->
                ProductResult.Success(
                    data = productEntities.map { productEntity ->
                        productMapper.mapToProduct(productEntity)
                    }
                )
            }
            .onStart<ProductResult<List<Product>>> { emit(ProductResult.Loading) }
            .catch { emit(ProductResult.Error(throwable = it)) }
            .flowOn(dispatchers.IO)
    }

    override fun getProductById(productId: Int): Flow<ProductResult<Product>> {
        return productDAO.getProductByID(productId)
            .map { productEntity ->
                ProductResult.Success(
                    data = productMapper.mapToProduct(productEntity)
                )
            }
            .onStart<ProductResult<Product>> { emit(ProductResult.Loading) }
            .catch { emit(ProductResult.Error(throwable = it)) }
            .flowOn(dispatchers.IO)
    }

    override suspend fun addProduct(product: Product) {
        productDAO.insert(productMapper.mapToProductEntity(product))
    }

    override suspend fun deleteProduct(productId: Int) {
        productDAO.delete(productId)
    }
}