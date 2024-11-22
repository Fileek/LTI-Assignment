package com.lti.assignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lti.assignment.data.ProductDatabase.Companion.PRODUCT_DATABASE_NAME

@Entity(tableName = PRODUCT_DATABASE_NAME)
data class ProductEntity(
    val name: String,
    val description: String,
    val price: Double,
    val rating: Float = 0.0f,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)