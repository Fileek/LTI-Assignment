package com.lti.assignment.data

import com.lti.assignment.MainDispatcherRule
import com.lti.assignment.domain.Product
import com.lti.assignment.utils.ProductResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class ProductRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: ProductRepositoryImpl

    @Mock
    private lateinit var productDAO: ProductDAO

    @Mock
    private lateinit var productMapper: ProductMapper

    @Mock
    private lateinit var dispatchers: Dispatchers

    private val productEntities = List(5) {
        ProductEntity(
            name = "Test Product",
            description = "Test Description",
            price = 9.99,
            id = it
        )
    }

    private val products = List(5) {
        Product(name = "Test Product", description = "Test Description", price = 9.99, id = it)
    }

    private val productEntity = productEntities.first()
    private val product = products.first()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        productEntities.forEachIndexed { index, productEntity ->
            whenever(productMapper.mapToProduct(productEntity)).thenReturn(products[index])
            whenever(productMapper.mapToProductEntity(products[index])).thenReturn(productEntity)
        }
        repository = ProductRepositoryImpl(dispatchers, productDAO, productMapper)
    }

    @Test
    fun `getAllProducts returns loading and success`() = runTest {
        val expectedResult = listOf(ProductResult.Loading, ProductResult.Success(products))
        whenever(productDAO.getAllProducts()).thenReturn(flowOf(productEntities))

        val result = repository.getAllProducts().toList()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `getAllProducts returns loading and error`() = runTest {
        val exception = RuntimeException("Test exception")
        val expectedResult = listOf(ProductResult.Loading, ProductResult.Error(exception))
        whenever(productDAO.getAllProducts()).thenReturn(flow { throw exception })

        val result = repository.getAllProducts().toList()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `getProductById returns loading and success`() = runTest {
        val expectedResult = listOf(ProductResult.Loading, ProductResult.Success(product))

        whenever(productDAO.getProductByID(1)).thenReturn(flowOf(productEntity))

        val result = repository.getProductById(1).toList()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `getProductById returns loading and error`() = runTest {
        val exception = RuntimeException("Test exception")
        val expectedResult = listOf(ProductResult.Loading, ProductResult.Error(exception))

        whenever(productDAO.getProductByID(1)).thenReturn(flow { throw exception })

        val result = repository.getProductById(1).toList()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `addProduct calls insert on DAO`() = runTest {
        repository.addProduct(product)

        verify(productDAO).insert(productEntity)
    }

    @Test
    fun `deleteProduct calls delete on DAO`() = runTest {
        repository.deleteProduct(1)

        verify(productDAO).delete(1)
    }
}