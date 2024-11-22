package com.lti.assignment.presentation.products

import com.lti.assignment.MainDispatcherRule
import com.lti.assignment.domain.Product
import com.lti.assignment.domain.ProductRepository
import com.lti.assignment.presentation.ProductModel
import com.lti.assignment.presentation.ProductModelMapper
import com.lti.assignment.utils.ProductResult
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ProductsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ProductsViewModel

    @Mock
    private lateinit var repository: ProductRepository

    @Mock
    private lateinit var productModelMapper: ProductModelMapper

    @Mock
    private lateinit var product: Product

    @Mock
    private lateinit var productModel: ProductModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        whenever(productModelMapper.mapToProductModel(product)).thenReturn(productModel)
    }

    @Test
    fun `fetchProducts success updates stateFlow with products`() = runTest {
        val expectedState = ProductsScreenState(products = listOf(productModel).toImmutableList())

        whenever(repository.getAllProducts())
            .thenReturn(flowOf(ProductResult.Success(listOf(product))))

        initViewModel()

        val actualState = viewModel.stateFlow.value

        assertEquals(expectedState, actualState)
    }

    @Test
    fun `fetchProducts error updates stateFlow with isError true`() = runTest {
        val expectedState = ProductsScreenState(isError = true)

        whenever(repository.getAllProducts())
            .thenReturn(flowOf(ProductResult.Error(Exception())))

        initViewModel()

        val actualState = viewModel.stateFlow.value

        assertEquals(expectedState, actualState)
    }

    @Test
    fun `fetchProducts loading updates stateFlow with isLoading true`() = runTest {
        val expectedState = ProductsScreenState(isLoading = true)

        whenever(repository.getAllProducts())
            .thenReturn(flowOf(ProductResult.Loading))

        initViewModel()

        val actualState = viewModel.stateFlow.value

        assertEquals(expectedState, actualState)
    }

    @Test
    fun `deleteProduct calls repository deleteProduct`() = runTest {
        val productId = 123

        whenever(repository.getAllProducts())
            .thenReturn(flowOf(ProductResult.Success(listOf(product))))

        initViewModel()

        viewModel.deleteProduct(productId)

        verify(repository).deleteProduct(productId)
    }

    @Test
    fun `retry calls fetchProducts`() = runTest {
        val expectedState = ProductsScreenState(products = listOf(productModel).toImmutableList())

        whenever(repository.getAllProducts())
            .thenReturn(flowOf(ProductResult.Success(listOf(product))))

        initViewModel()

        viewModel.retry()

        val actualState = viewModel.stateFlow.value

        assertEquals(expectedState, actualState)
        verify(repository, times(2)).getAllProducts()
    }

    private fun initViewModel() {
        viewModel = ProductsViewModel(repository, productModelMapper)
    }
}