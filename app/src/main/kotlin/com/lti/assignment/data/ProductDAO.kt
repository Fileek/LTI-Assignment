package com.lti.assignment.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lti.assignment.data.ProductDatabase.Companion.PRODUCT_DATABASE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Query("SELECT * FROM $PRODUCT_DATABASE_NAME")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM $PRODUCT_DATABASE_NAME WHERE id = :id")
    fun getProductByID(id: Int): Flow<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: Iterable<ProductEntity>)

    @Query("DELETE FROM $PRODUCT_DATABASE_NAME WHERE id = :id")
    suspend fun delete(id: Int)
}