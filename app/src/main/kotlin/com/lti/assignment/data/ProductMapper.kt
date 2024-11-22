package com.lti.assignment.data

import com.lti.assignment.domain.Product
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun mapToProductEntity(product: Product): ProductEntity {
        return ProductEntity(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price
        )
    }

    fun mapToProduct(productEntity: ProductEntity): Product {
        return Product(
            id = productEntity.id,
            name = productEntity.name,
            description = productEntity.description,
            price = productEntity.price,
        )
    }
}