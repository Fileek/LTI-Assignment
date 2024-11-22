package com.lti.assignment.utils

sealed interface ProductResult<out T> {

    data object Loading : ProductResult<Nothing>

    data class Success<out T : Any>(val data: T) : ProductResult<T>

    data class Error(val throwable: Throwable) : ProductResult<Nothing>
}