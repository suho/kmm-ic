package co.nimblehq.ic.kmm.suv.android.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import co.nimblehq.ic.kmm.suv.android.util.EmojiParameterProvider

const val THUMPS_UP_EMOJI = "\uD83D\uDC4D\uD83C\uDFFB" // 👍🏻
const val HEART_EMOJI = "❤️"
const val STAR_EMOJI = "⭐️️"
const val POUTING_FACE_EMOJI = "\uD83D\uDE21" // 😡
const val CONFUSED_FACE_EMOJI = "\uD83D\uDE15" // 😕
const val NEUTRAL_FACE_EMOJI = "\uD83D\uDE10" // 😐
const val SLIGHTLY_SMILING_FACE_EMOJI = "\uD83D\uDE42" // 🙂
const val GRINNING_FACE_WITH_SMILING_EYES_EMOJI = "\uD83D\uDE04" // 😄

enum class EmojiHighlightStyle {
    LEFT_ITEMS, ONE
}

@Composable
fun EmojiRatingAnswer(
    emojis: List<@Composable () -> Unit>,
    onIndexChange: (Int) -> Unit,
    currentIndex: Int = -1,
    highlightStyle: EmojiHighlightStyle = EmojiHighlightStyle.LEFT_ITEMS
) {
    var selectedIndex by remember { mutableStateOf(currentIndex) }
    LazyRow {
        items(emojis.size) { index ->
            val isHighlighted =
                (highlightStyle == EmojiHighlightStyle.LEFT_ITEMS && index <= selectedIndex)
                        || (highlightStyle == EmojiHighlightStyle.ONE && index == selectedIndex)
            val alpha = if (isHighlighted) 1f else 0.5f
            Button(
                onClick = {
                    selectedIndex = index
                    onIndexChange(index)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                elevation = null,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .alpha(alpha)
                    .size(34.dp)
            ) {
                emojis[index]()
            }
            if (index != emojis.size - 1) {
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun Emoji(name: String) {
    Text(text = name, style = Typography.h5)
}

@Preview
@Composable
fun LeftEmojiRatingAnswerPreview(
    @PreviewParameter(EmojiParameterProvider::class) emojiName: String
) {
    EmojiRatingAnswer(
        emojis = MutableList(5) {
            { Emoji(name = emojiName) }
        },
        onIndexChange = {}
    )
}

@Preview
@Composable
fun OneEmojiRatingAnswerPreview() {
    EmojiRatingAnswer(
        emojis = listOf(
            { Emoji(name = POUTING_FACE_EMOJI) },
            { Emoji(name = CONFUSED_FACE_EMOJI) },
            { Emoji(name = NEUTRAL_FACE_EMOJI) },
            { Emoji(name = SLIGHTLY_SMILING_FACE_EMOJI) },
            { Emoji(name = GRINNING_FACE_WITH_SMILING_EYES_EMOJI) }
        ),
        onIndexChange = {},
        highlightStyle = EmojiHighlightStyle.ONE
    )
}
