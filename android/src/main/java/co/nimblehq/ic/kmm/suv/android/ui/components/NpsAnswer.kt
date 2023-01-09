package co.nimblehq.ic.kmm.suv.android.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import co.nimblehq.ic.kmm.suv.domain.model.Answer
import co.nimblehq.ic.kmm.suv.domain.model.AnswerInput
import co.nimblehq.ic.kmm.suv.domain.model.Answerable

private const val NPS_SIZE = 10
private val NPS_VIEW_HEIGHT = 56.dp
private val NPS_VIEW_WIDTH = 345.5.dp

object NpsAnswerContentDescription {
    private const val NPS = "NPS"

    fun nps(index: Int) = "$NPS-$index"
}

@Composable
fun NpsAnswer(
    answers: List<Answerable>,
    onInputChange: (AnswerInput) -> Unit,
    input: AnswerInput? = null
) {
    var currentInput by remember { mutableStateOf(input) }
    val currentIndex = answers.map { it.id }.indexOf(currentInput?.id.orEmpty())
    Column {
        Row(
            modifier = Modifier
                .border(BorderStroke(0.5.dp, Color.White), AppTheme.shapes.medium)
        ) {
            answers.forEachIndexed { index, answer ->
                val isHighlight = index <= currentIndex
                val alpha = if (isHighlight) 1f else 0.5f
                Button(
                    onClick = {
                        val newInput = AnswerInput.Select(answer.id)
                        currentInput = newInput
                        onInputChange(newInput)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                    elevation = null,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .alpha(alpha)
                        .width(34.dp)
                        .height(NPS_VIEW_HEIGHT)
                        .semantics {
                            contentDescription = NpsAnswerContentDescription.nps(index)
                        }
                ) {
                    Text(
                        text = "${index + 1}",
                        color = Color.White,
                        style = if (isHighlight) Typography.h6 else Typography.h6.copy(fontWeight = FontWeight.Normal)
                    )
                }
                if (index != NPS_SIZE - 1) {
                    Spacer(
                        modifier = Modifier
                            .width(0.5.dp)
                            .height(NPS_VIEW_HEIGHT)
                            .background(Color.White)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.width(NPS_VIEW_WIDTH)
        ) {
            val leftRangeAlpha = if (currentIndex < answers.size / 2) 1f else 0.5f
            Text(
                text = stringResource(id = R.string.nps_not_at_all_likely),
                color = Color.White,
                style = Typography.subtitle2,
                modifier = Modifier.alpha(leftRangeAlpha)
            )
            Text(
                text = stringResource(id = R.string.nps_extremely_likely),
                color = Color.White,
                style = Typography.subtitle2,
                modifier = Modifier.alpha(1.5f - leftRangeAlpha)
            )
        }
    }
}

@Preview
@Composable
fun NpsAnswerPreview() {
    NpsAnswer(
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
