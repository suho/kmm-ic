package co.nimblehq.ic.kmm.suv.android.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.ic.kmm.suv.android.R

@Composable
fun ErrorAlertDialog(
    message: String,
    onButtonClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onButtonClick() },
        title = { Text(text = stringResource(id = R.string.app_name)) },
        text = { Text(text = message) },
        backgroundColor = Color.White,
        confirmButton = {
            Button(
                onClick = { onButtonClick() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.error_button_confirm),
                    color = Color.White
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ErrorAlertDialogPreview() {
    ErrorAlertDialog(
        message = "Error Message!",
        onButtonClick = {}
    )
}
