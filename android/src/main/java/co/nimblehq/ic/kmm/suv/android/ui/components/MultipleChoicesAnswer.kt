package co.nimblehq.ic.kmm.suv.android.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import co.nimblehq.ic.kmm.suv.domain.model.Answer
import co.nimblehq.ic.kmm.suv.domain.model.AnswerInput
import co.nimblehq.ic.kmm.suv.domain.model.Answerable

object MultipleChoicesAnswerContentDescription {
    private const val CHOICE = "CHOICE"

    fun choice(index: Int) = "$CHOICE-$index"
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MultipleChoicesAnswer(
    answers: List<Answerable>,
    onInputChange: (Set<AnswerInput>) -> Unit,
    input: Set<AnswerInput> = mutableSetOf()
) {
    var currentInput by remember { mutableStateOf(input) }
    Column(modifier = Modifier.padding(horizontal = 80.dp)) {
        answers.forEachIndexed { index, choice ->
            val isHighlight = currentInput.contains(AnswerInput.Select(choice.id))
            Button(
                onClick = {
                    currentInput = if (currentInput.contains(AnswerInput.Select(choice.id))) {
                        currentInput.minus(AnswerInput.Select(choice.id))
                    } else {
                        currentInput.plus(AnswerInput.Select(choice.id))
                    }
                    onInputChange(currentInput)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                elevation = null,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = MultipleChoicesAnswerContentDescription.choice(index)
                    }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight()
                ) {
                    Text(
                        text = choice.content.orEmpty(),
                        color = if (isHighlight) Color.White else Color.White.copy(alpha = 0.5f),
                        style = if (isHighlight) Typography.h6 else Typography.h6.copy(fontWeight = FontWeight.Normal),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    val imageId =
                        if (isHighlight) R.drawable.ic_checkbox_selected else R.drawable.ic_checkbox_unselected
                    Image(
                        painter = painterResource(id = imageId),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .width(28.dp)
                            .height(30.dp)
                    )
                }
            }
            if (index < answers.size - 1) {
                Spacer(
                    modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                )
            }
        }
    }
}

@Preview
@Composable
fun MultipleChoicesAnswerPreview() {
    MultipleChoicesAnswer(
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
