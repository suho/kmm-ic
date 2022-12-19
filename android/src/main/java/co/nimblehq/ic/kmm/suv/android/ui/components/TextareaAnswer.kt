package co.nimblehq.ic.kmm.suv.android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import co.nimblehq.ic.kmm.suv.domain.model.Answer
import co.nimblehq.ic.kmm.suv.domain.model.AnswerInput
import co.nimblehq.ic.kmm.suv.domain.model.Answerable
import co.nimblehq.ic.kmm.suv.domain.model.getContentType

object TextareaAnswerContentDescription {
    const val TEXTAREA = "TEXTAREA"
}

@Suppress("NAME_SHADOWING")
@Composable
fun TextareaAnswer(
    answer: Answerable,
    onInputChange: (AnswerInput) -> Unit,
    modifier: Modifier = Modifier,
    input: AnswerInput? = null
) {
    var input by remember { mutableStateOf(input) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(input) {
        input?.let { onInputChange(it) }
    }

    Box(modifier = modifier) {
        TextField(
            value = input?.getContentType()?.content.orEmpty(),
            onValueChange = { input = AnswerInput.Content(answer.id, it) },
            placeholder = {
                answer.placeholder.orEmpty().let {
                    Text(
                        text = it,
                        style = Typography.subtitle1
                    )
                }
            },
            colors = textareaColors(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier
                .fillMaxSize()
                .clip(AppTheme.shapes.large)
                .semantics {
                    contentDescription = TextareaAnswerContentDescription.TEXTAREA
                }
        )
    }
}

@Composable
private fun textareaColors(): TextFieldColors = TextFieldDefaults.textFieldColors(
    textColor = Color.White,
    backgroundColor = Color.White.copy(0.18f),
    cursorColor = Color.White,
    unfocusedIndicatorColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    placeholderColor = Color.White.copy(0.3f)
)

@Preview
@Composable
fun TextareaAnswerPreview() {
    TextareaAnswer(
        answer = Answer(
            id = "1",
            text = "email",
            displayOrder = 0,
            inputMaskPlaceholder = "Email"
        ),
        onInputChange = {},
        modifier = Modifier
            .width(327.dp)
            .height(168.dp)
    )
}
