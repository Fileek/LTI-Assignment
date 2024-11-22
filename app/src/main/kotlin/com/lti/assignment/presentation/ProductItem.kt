package com.lti.assignment.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lti.assignment.R
import com.lti.assignment.theme.LTIAssignmentTheme

@Composable
fun ProductItem(
    product: ProductModel,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.outlinedCardColors(),
        elevation = CardDefaults.outlinedCardElevation(),
        border = CardDefaults.outlinedCardBorder(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                Text(text = product.name)
                Text(text = product.formattedPrice)
            }
            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductItemPreview() {
    LTIAssignmentTheme {
        ProductItem(
            product = ProductModel(
                name = "Product Name",
                description = "Product Description",
                rating = 4.5f,
                price = 100.0
            ),
            onDelete = {},
            onClick = {}
        )
    }
}