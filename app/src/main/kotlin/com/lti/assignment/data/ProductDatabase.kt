package com.lti.assignment.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lti.assignment.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDAO

    private class ProductDatabaseCallback(
        private val jsonParser: JSONParser,
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch {
                try {
                    val jsonString = jsonParser.parseJSON(R.raw.products)
                    val listType = object : TypeToken<List<ProductEntity>>() {}.type
                    val products = Gson().fromJson<List<ProductEntity>>(jsonString, listType)

                    INSTANCE?.productDao()?.insertAll(products)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        const val PRODUCT_DATABASE_NAME = "product_database"

        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getDatabase(
            jsonParser: JSONParser,
            context: Context,
            scope: CoroutineScope
        ): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    PRODUCT_DATABASE_NAME
                )
                    .addCallback(ProductDatabaseCallback(jsonParser, scope))
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}