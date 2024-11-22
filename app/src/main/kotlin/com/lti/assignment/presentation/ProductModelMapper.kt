package com.lti.assignment.presentation

import com.lti.assignment.domain.Product
import javax.inject.Inject

class ProductModelMapper @Inject constructor() {

    fun mapToProductModel(product: Product): ProductModel {
        return ProductModel(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            rating = product.rating,
        )
    }

    fun mapToProduct(productModel: ProductModel): Product {
        return Product(
            id = productModel.id,
            name = productModel.name,
            description = productModel.description,
            price = productModel.price,
            rating = productModel.rating,
        )
    }
}