package co.nimblehq.ic.kmm.suv.android.ui.components

import androidx.compose.foundation.layout.*
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

object FormAnswerContentDescription {
    private const val TEXT_FIELD = "TEXT_FIELD"

    fun field(index: Int) = "$TEXT_FIELD-$index"
}

@Composable
fun FormAnswer(
    answers: List<Answerable>,
    onInputChange: (List<AnswerInput>) -> Unit,
    modifier: Modifier = Modifier,
    input: List<AnswerInput> = emptyList()
) {
    val defaultInputs: List<AnswerInput> = answers.map { AnswerInput.Content(it.id, "") }
    var changes by remember {
        if (input.isEmpty()) {
            mutableStateOf(defaultInputs)
        } else {
            mutableStateOf(input)
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        answers.forEachIndexed { index, answer ->
            var value by remember {
                mutableStateOf(changes.elementAtOrNull(index)?.getContentType()?.content.orEmpty())
            }
            val focusManager = LocalFocusManager.current
            TextField(
                value = value,
                onValueChange = {
                    value = it
                    val newInput = AnswerInput.Content(answer.id, it)
                    val newChanges = changes.toMutableList()
                    newChanges[index] = newInput
                    changes = newChanges
                    onInputChange(newChanges)
                },
                singleLine = true,
                placeholder = {
                    Text(
                        text = answer.placeholder.orEmpty(),
                        style = Typography.subtitle1
                    )
                },
                colors = formTextFieldColors(value.isEmpty()),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(AppTheme.dimensions.defaultComponentHeight)
                    .padding(start = 24.dp, end = 24.dp)
                    .clip(AppTheme.shapes.large)
                    .semantics {
                        contentDescription = FormAnswerContentDescription.field(index)
                    }
            )
        }
    }
}

@Composable
private fun formTextFieldColors(isEmpty: Boolean): TextFieldColors =
    TextFieldDefaults.textFieldColors(
        textColor = Color.White,
        backgroundColor = Color.White.copy(if (isEmpty) 0.2f else 0.3f),
        cursorColor = Color.White,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        placeholderColor = Color.White.copy(0.3f)
    )

@Preview
@Composable
fun FormAnswerPreview() {
    FormAnswer(
        answers = listOf(
            Answer(
                id = "1",
                text = "email",
                displayOrder = 0,
                inputMaskPlaceholder = "Email"
            ),
            Answer(
                id = "2",
                text = "password",
                displayOrder = 1,
                inputMaskPlaceholder = "Password"
            )
        ),
        onInputChange = {}
    )
}
