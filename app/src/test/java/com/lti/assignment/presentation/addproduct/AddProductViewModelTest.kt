package com.lti.assignment.presentation.addproduct

import com.lti.assignment.MainDispatcherRule
import com.lti.assignment.domain.Product
import com.lti.assignment.domain.ProductRepository
import com.lti.assignment.presentation.ProductModel
import com.lti.assignment.presentation.ProductModelMapper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyBlocking
import org.mockito.kotlin.whenever

class AddProductViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AddProductViewModel

    @Mock
    private lateinit var repository: ProductRepository

    @Mock
    private lateinit var productModelMapper: ProductModelMapper

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        viewModel = AddProductViewModel(
            repository = repository,
            productModelMapper = productModelMapper
        )
    }

    @Test
    fun `addProduct must map the product and add it through repository`() {
        val productModel = mock<ProductModel>()
        val product = mock<Product>()

        whenever(productModelMapper.mapToProduct(productModel)).thenReturn(product)

        viewModel.addProduct(productModel)

        verifyBlocking(repository) {
            addProduct(product)
        }
    }
}