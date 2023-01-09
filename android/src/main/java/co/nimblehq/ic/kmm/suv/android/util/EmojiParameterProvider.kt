package co.nimblehq.ic.kmm.suv.android.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import co.nimblehq.ic.kmm.suv.android.ui.components.EMOJI_HEART
import co.nimblehq.ic.kmm.suv.android.ui.components.EMOJI_STAR
import co.nimblehq.ic.kmm.suv.android.ui.components.EMOJI_THUMPS_UP

class EmojiParameterProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        EMOJI_THUMPS_UP,
        EMOJI_HEART,
        EMOJI_STAR
    )
}
