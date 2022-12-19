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

const val EMOJI_THUMPS_UP = "\uD83D\uDC4D\uD83C\uDFFB" // 👍🏻
const val EMOJI_HEART = "❤️"
const val EMOJI_STAR = "⭐️️"
const val EMOJI_POUTING_FACE = "\uD83D\uDE21" // 😡
const val EMOJI_CONFUSED_FACE = "\uD83D\uDE15" // 😕
const val EMOJI_NEUTRAL_FACE = "\uD83D\uDE10" // 😐
const val EMOJI_SLIGHTLY_SMILING_FACE = "\uD83D\uDE42" // 🙂
const val EMOJI_GRINNING_FACE_WITH_SMILING_EYES = "\uD83D\uDE04" // 😄

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
            val isHighlighted = when (highlightStyle) {
                EmojiHighlightStyle.ONE -> index == selectedIndex
                EmojiHighlightStyle.LEFT_ITEMS -> index <= selectedIndex
            }
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
            { Emoji(name = EMOJI_POUTING_FACE) },
            { Emoji(name = EMOJI_CONFUSED_FACE) },
            { Emoji(name = EMOJI_NEUTRAL_FACE) },
            { Emoji(name = EMOJI_SLIGHTLY_SMILING_FACE) },
            { Emoji(name = EMOJI_GRINNING_FACE_WITH_SMILING_EYES) }
        ),
        onIndexChange = {},
        highlightStyle = EmojiHighlightStyle.ONE
    )
}
