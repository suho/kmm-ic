package co.nimblehq.ic.kmm.suv.android.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import co.nimblehq.ic.kmm.suv.android.ui.components.HEART_EMOJI
import co.nimblehq.ic.kmm.suv.android.ui.components.STAR_EMOJI
import co.nimblehq.ic.kmm.suv.android.ui.components.THUMPS_UP_EMOJI

class EmojiParameterProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        THUMPS_UP_EMOJI,
        HEART_EMOJI,
        STAR_EMOJI
    )
}
