package com.lti.assignment.presentation.details

import androidx.lifecycle.SavedStateHandle
import com.lti.assignment.MainDispatcherRule
import com.lti.assignment.domain.Product
import com.lti.assignment.domain.ProductRepository
import com.lti.assignment.presentation.ProductModel
import com.lti.assignment.presentation.ProductModelMapper
import com.lti.assignment.utils.ProductResult
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

class ProductDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ProductDetailsViewModel

    @Mock
    private lateinit var repository: ProductRepository

    @Mock
    private lateinit var productModelMapper: ProductModelMapper

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

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
    fun `fetchProduct success updates stateFlow with productModel`() = runTest {
        val expectedState = ProductDetailsScreenState(productModel = productModel)

        whenever(repository.getProductById(PRODUCT_ID))
            .thenReturn(flowOf(ProductResult.Success(product)))

        initViewModel()

        val actualState = viewModel.stateFlow.value

        assertEquals(expectedState, actualState)
    }

    @Test
    fun `fetchProduct error updates stateFlow with isError true`() = runTest {
        val expectedState = ProductDetailsScreenState(isError = true)

        whenever(repository.getProductById(PRODUCT_ID))
            .thenReturn(flowOf(ProductResult.Error(Exception())))

        initViewModel()

        val actualState = viewModel.stateFlow.value

        assertEquals(expectedState, actualState)
    }

    @Test
    fun `fetchProduct loading updates stateFlow with isLoading true`() = runTest {
        val expectedState = ProductDetailsScreenState(isLoading = true)

        whenever(repository.getProductById(PRODUCT_ID))
            .thenReturn(flowOf(ProductResult.Loading))

        initViewModel()

        val actualState = viewModel.stateFlow.value

        assertEquals(expectedState, actualState)
    }

    @Test
    fun `retry calls fetchProduct`() = runTest {
        val expectedState = ProductDetailsScreenState(productModel = productModel)

        whenever(repository.getProductById(PRODUCT_ID))
            .thenReturn(flowOf(ProductResult.Success(product)))

        initViewModel()

        viewModel.retry()

        val actualState = viewModel.stateFlow.value

        assertEquals(expectedState, actualState)
        verify(repository, times(2)).getProductById(PRODUCT_ID)
    }

    private fun initViewModel() {
        viewModel = ProductDetailsViewModel(repository, productModelMapper, savedStateHandle)
    }

    private companion object {
        const val PRODUCT_ID = 0
    }
}
