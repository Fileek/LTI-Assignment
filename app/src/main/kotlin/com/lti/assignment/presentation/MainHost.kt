package com.lti.assignment.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lti.assignment.presentation.addproduct.AddProductScreen
import com.lti.assignment.presentation.details.ProductDetailsScreen
import com.lti.assignment.presentation.products.ProductsScreen
import com.lti.assignment.utils.LocalNavController

@Composable
fun MainHost() {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = Route.Products
        ) {
            composable<Route.Products> { ProductsScreen() }
            composable<Route.ProductDetails> { ProductDetailsScreen() }
            composable<Route.AddProduct> { AddProductScreen() }
        }
    }
}