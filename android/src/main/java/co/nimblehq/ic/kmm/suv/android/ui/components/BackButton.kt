package co.nimblehq.ic.kmm.suv.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.R

const val BACK_BUTTON_CONTENT_DESCRIPTION = "BACK_BUTTON"

@Composable
fun BackButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        elevation = null,
        contentPadding = PaddingValues(14.dp),
        modifier = Modifier
            .semantics {
                contentDescription = BACK_BUTTON_CONTENT_DESCRIPTION
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.size(30.dp),
        )
    }
}
