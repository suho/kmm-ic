package co.nimblehq.ic.kmm.suv.android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography

@Composable
fun PrimaryButton(text: String, isLoading: Boolean, onClick: () -> Unit) {
    Button(
        shape = AppTheme.shapes.medium,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        ),
        enabled = !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.dimensions.defaultComponentHeight)
            .padding(start = 24.dp, end = 24.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.Black)
        } else {
            Text(
                text = text,
                color = Color.Black,
                style = Typography.subtitle2
            )
        }
    }
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    PrimaryButton(text = "Log In", isLoading = false) {}
}

@Preview
@Composable
fun LoadingPrimaryButtonPreview() {
    PrimaryButton(text = "Log In", isLoading = true) {}
}
