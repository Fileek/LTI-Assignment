package com.lti.assignment.presentation.foundation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lti.assignment.theme.LTIAssignmentTheme

@Composable
fun ButtonsRow(
    primaryLabel: String,
    onPrimaryClick: () -> Unit,
    secondaryLabel: String,
    onSecondaryClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        OutlinedButton(
            onClick = onSecondaryClick,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            Text(secondaryLabel)
        }
        Button(
            onClick = onPrimaryClick,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            Text(primaryLabel)
        }
    }
}

@Preview
@Composable
private fun ButtonsRowPreview() {
    LTIAssignmentTheme {
        ButtonsRow(
            primaryLabel = "Primary",
            onPrimaryClick = {},
            secondaryLabel = "Secondary",
            onSecondaryClick = {}
        )
    }
}