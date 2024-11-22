package com.lti.assignment.presentation.foundation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lti.assignment.R

@Composable
fun ErrorContent(
    modifier: Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.error),
            fontSize = 20.sp
        )
        Button(
            onClick = onRetry,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}