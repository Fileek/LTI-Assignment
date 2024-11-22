package com.lti.assignment.presentation.foundation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lti.assignment.R
import com.lti.assignment.theme.LTIAssignmentTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ConfirmationDialog(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = title)
                Spacer(modifier = Modifier.height(32.dp))
                ButtonsRow(
                    primaryLabel = stringResource(R.string.yes),
                    onPrimaryClick = onConfirm,
                    secondaryLabel = stringResource(R.string.no),
                    onSecondaryClick = onDismiss
                )
            }
        }
    }
}

@Preview
@Composable
fun ConfirmationDialogPreview() {
    LTIAssignmentTheme {
        ConfirmationDialog(
            title = "Are you sure?",
            onDismiss = {},
            onConfirm = {}
        )
    }
}