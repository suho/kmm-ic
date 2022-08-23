package co.nimblehq.suvkmmsurveys.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import co.nimblehq.suvkmmsurveys.ui.theme.Dimension.SpacingNormal
import co.nimblehq.suvkmmsurveys.ui.theme.Theme

@Composable
fun HomeScreen(
    title: String
) {
    Theme {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentHeight()
                .padding(all = SpacingNormal)
        )
    }
}
