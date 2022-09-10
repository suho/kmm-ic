package co.nimblehq.ic.kmm.suv.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.ui.theme.Shapes
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography

@Composable
fun PrimaryButton(title: String, onClick: () -> Unit) {
    Button(
        shape = Shapes.medium,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 24.dp, end = 24.dp)
            .background(Color.Transparent)
    ) {
        Text(
            text = title,
            color = Color.Black,
            style = Typography.subtitle2
        )
    }
}
