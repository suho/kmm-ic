package co.nimblehq.ic.kmm.suv.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.util.lerp
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import co.nimblehq.ic.kmm.suv.domain.model.Answer
import co.nimblehq.ic.kmm.suv.domain.model.AnswerInput
import co.nimblehq.ic.kmm.suv.domain.model.Answerable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

object DropdownAnswerContentDescription {
    const val TEXT = "DROPDOWN_ANSWER_TEXT"
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DropdownAnswer(
    answers: List<Answerable>,
    onInputChange: (AnswerInput) -> Unit,
    input: AnswerInput? = null
) {
    val padding = 56.dp
    val pagerState = rememberPagerState()
    LaunchedEffect(pagerState.currentPage) {
        onInputChange(
            AnswerInput.Select(answers.elementAtOrNull(pagerState.currentPage)?.id.orEmpty())
        )
    }
    LaunchedEffect(input) {
        if (input != null) {
            pagerState.animateScrollToPage(answers.indexOfFirst { it.id == input.id })
        }
    }
    Box {
        VerticalPager(
            count = answers.size,
            state = pagerState,
            contentPadding = PaddingValues(vertical = padding),
            modifier = Modifier
                .height(170.dp)
                .fillMaxWidth()
        ) { page ->
            val choice = answers[page]
            val isHighlight = pagerState.currentPage == page
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(AppTheme.dimensions.defaultComponentHeight)
                    .fillMaxWidth()
            ) {
                Text(
                    text = choice.content.orEmpty(),
                    color = Color.White,
                    style = if (isHighlight) Typography.h6 else Typography.h6.copy(fontWeight = FontWeight.Normal),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                        .semantics {
                            contentDescription = DropdownAnswerContentDescription.TEXT
                        }
                )
            }
        }
        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier.padding(top = padding)
        )
        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 2 * padding)
        )
    }
}

@Preview
@Composable
fun DropdownAnswerPreview() {
    DropdownAnswer(
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
            )
        ),
        onInputChange = {}
    )
}
