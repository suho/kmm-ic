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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography

typealias Placeholder = String

@Composable
fun FormAnswer(
    placeholders: List<Placeholder>,
    onTextChange: (Pair<Int, String>) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        placeholders.forEachIndexed { index, placeholder ->
            var value by remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current
            TextField(
                value = value,
                onValueChange = {
                    value = it
                    onTextChange(Pair(index, it))
                },
                singleLine = true,
                placeholder = {
                    Text(
                        text = placeholder,
                        style = Typography.subtitle1
                    )
                },
                colors = formTextFieldColors(value.isEmpty()),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(56.dp)
                    .padding(start = 24.dp, end = 24.dp)
                    .clip(AppTheme.shapes.large)
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
        placeholders = listOf("Email", "Password"),
        onTextChange = {}
    )
}
