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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography

@Composable
fun TextareaAnswer(
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
) {
    var value by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(value) {
        onTextChange(value)
    }

    Box(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = { value = it },
            placeholder = {
                Text(
                    text = placeholder,
                    style = Typography.subtitle1
                )
            },
            colors = textareaColors(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier
                .fillMaxSize()
                .clip(AppTheme.shapes.large)
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
        onTextChange = {},
        placeholder = "Your thought",
        modifier = Modifier
            .width(327.dp)
            .height(168.dp)
    )
}
