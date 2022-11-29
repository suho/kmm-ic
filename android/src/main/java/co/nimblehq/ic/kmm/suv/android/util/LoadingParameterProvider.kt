package co.nimblehq.ic.kmm.suv.android.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class LoadingParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(
        true,
        false
    )
}
