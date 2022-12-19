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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import co.nimblehq.ic.kmm.suv.android.util.EmojiParameterProvider
import co.nimblehq.ic.kmm.suv.domain.model.Answer
import co.nimblehq.ic.kmm.suv.domain.model.AnswerInput
import co.nimblehq.ic.kmm.suv.domain.model.Answerable

const val EMOJI_THUMPS_UP = "\uD83D\uDC4D\uD83C\uDFFB" // 👍🏻
const val EMOJI_HEART = "❤️"
const val EMOJI_STAR = "⭐️️"
const val EMOJI_POUTING_FACE = "\uD83D\uDE21" // 😡
const val EMOJI_CONFUSED_FACE = "\uD83D\uDE15" // 😕
const val EMOJI_NEUTRAL_FACE = "\uD83D\uDE10" // 😐
const val EMOJI_SLIGHTLY_SMILING_FACE = "\uD83D\uDE42" // 🙂
const val EMOJI_GRINNING_FACE_WITH_SMILING_EYES = "\uD83D\uDE04" // 😄

object EmojiRatingAnswerContentDescription {
    private const val BUTTON_EMOJI = "BUTTON_EMOJI"

    fun emoji(questionIndex: Int, answerIndex: Int) = "$BUTTON_EMOJI-$questionIndex-$answerIndex"
}

enum class EmojiHighlightStyle {
    LEFT_ITEMS, ONE
}

@Suppress("NAME_SHADOWING")
@Composable
fun EmojiRatingAnswer(
    emojis: List<@Composable () -> Unit>,
    answers: List<Answerable>,
    onInputChange: (AnswerInput) -> Unit,
    input: AnswerInput? = null,
    highlightStyle: EmojiHighlightStyle = EmojiHighlightStyle.LEFT_ITEMS,
    questionIndex: Int = 0 // TODO: For UITest, improve later
) {
    var currentInput by remember { mutableStateOf(input) }
    val currentIndex = answers.map { it.id }.indexOf(currentInput?.id.orEmpty())
    LazyRow {
        items(emojis.size) { index ->
            val isHighlighted = when (highlightStyle) {
                EmojiHighlightStyle.ONE -> index == currentIndex
                EmojiHighlightStyle.LEFT_ITEMS -> index <= currentIndex
            }
            val alpha = if (isHighlighted) 1f else 0.5f
            Button(
                onClick = {
                    val input = AnswerInput.Select(answers.elementAtOrNull(index)?.id.orEmpty())
                    currentInput = input
                    onInputChange(input)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                elevation = null,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .alpha(alpha)
                    .size(34.dp)
                    .semantics {
                        contentDescription =
                            EmojiRatingAnswerContentDescription.emoji(questionIndex, index)
                    }
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
        emojis = List(5) {
            { Emoji(name = emojiName) }
        },
        answers = listOf(
            Answer(
                id = "1",
                text = "choice 1",
                displayOrder = 0,
                inputMaskPlaceholder = null
            ),
            Answer(
                id = "2",
                text = "Choice 2",
                displayOrder = 1,
                inputMaskPlaceholder = null
            ),
            Answer(
                id = "3",
                text = "choice 3",
                displayOrder = 0,
                inputMaskPlaceholder = null
            ),
            Answer(
                id = "4",
                text = "Choice 4",
                displayOrder = 1,
                inputMaskPlaceholder = null
            ),
            Answer(
                id = "5",
                text = "choice 5",
                displayOrder = 0,
                inputMaskPlaceholder = null
            )
        ),
        onInputChange = {},
    )
}

@Preview
@Composable
fun OneEmojiRatingAnswerPreview() {
    EmojiRatingAnswer(
        emojis = listOf(
            EMOJI_POUTING_FACE,
            EMOJI_CONFUSED_FACE,
            EMOJI_NEUTRAL_FACE,
            EMOJI_SLIGHTLY_SMILING_FACE,
            EMOJI_GRINNING_FACE_WITH_SMILING_EYES
        ).map { { Emoji(name = it) } },
        answers = listOf(
            Answer(
                id = "1",
                text = "choice 1",
                displayOrder = 0,
                inputMaskPlaceholder = null
            ),
            Answer(
                id = "2",
                text = "Choice 2",
                displayOrder = 1,
                inputMaskPlaceholder = null
            ),
            Answer(
                id = "3",
                text = "choice 3",
                displayOrder = 0,
                inputMaskPlaceholder = null
            ),
            Answer(
                id = "4",
                text = "Choice 4",
                displayOrder = 1,
                inputMaskPlaceholder = null
            ),
            Answer(
                id = "5",
                text = "choice 5",
                displayOrder = 0,
                inputMaskPlaceholder = null
            )
        ),
        onInputChange = {},
        highlightStyle = EmojiHighlightStyle.ONE
    )
}
