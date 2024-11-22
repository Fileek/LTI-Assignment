package com.lti.assignment.di

import android.content.Context
import com.lti.assignment.data.JSONParser
import com.lti.assignment.data.JSONParserImpl
import com.lti.assignment.data.ProductDAO
import com.lti.assignment.data.ProductDatabase
import com.lti.assignment.data.ProductMapper
import com.lti.assignment.data.ProductRepositoryImpl
import com.lti.assignment.domain.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Named("IOScope")
    fun provideIOScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Provides
    fun provideProductDao(database: ProductDatabase): ProductDAO {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideProductDatabase(
        jsonParser: JSONParser,
        @ApplicationContext context: Context,
        @Named("IOScope") coroutineScope: CoroutineScope
    ): ProductDatabase {
        return ProductDatabase.getDatabase(jsonParser, context, coroutineScope)
    }

    @Provides
    fun provideProductRepository(
        productDao: ProductDAO,
        productMapper: ProductMapper
    ): ProductRepository {
        return ProductRepositoryImpl(
            dispatchers = Dispatchers,
            productDAO = productDao,
            productMapper = productMapper
        )
    }

    @Provides
    @Singleton
    fun provideJSONParser(
        @ApplicationContext context: Context
    ): JSONParser {
        return JSONParserImpl(context)
    }
}