package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.extension.placeholder
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import co.nimblehq.ic.kmm.suv.android.util.LoadingParameterProvider
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@Composable
fun SurveyQuestionsScreen(
    onCloseClick: () -> Unit
) {
    SurveyQuestionsScreenContent(
        // TODO: Remove dummy data
        SurveyQuestionsContentUiModel(
            isLoading = false,
            backgroundUrl = "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l",
            questions = listOf(
                QuestionContentUiModel(
                    "1/2",
                    "What your name?"
                ),
                QuestionContentUiModel(
                    "2/2",
                    "How old are you?"
                )
            )
        ),
        onCloseClick
    )
}

@Composable
private fun SurveyQuestionsScreenContent(
    uiModel: SurveyQuestionsContentUiModel,
    onCloseClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.bg_splash),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            )
                        )
                    )
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
    ) {
        Button(
            onClick = onCloseClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = null,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .width(28.dp)
                    .height(30.dp)
            )
        }
    }
    if (uiModel.isLoading) {
        SurveyQuestionsLoadingContent()
    } else {
        SurveyQuestionsContent(uiModel.questions)
    }
}

@Composable
private fun SurveyQuestionsLoadingContent() {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = AppTheme.dimensions.mediumPadding)
    ) {
        Spacer(modifier = Modifier.height(68.dp))
        Spacer(
            modifier = Modifier
                .size(50.dp, 16.dp)
                .placeholder(true, 8.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(
            modifier = Modifier
                .size(250.dp, 18.dp)
                .placeholder(true, 9.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Spacer(
            modifier = Modifier
                .size(200.dp, 18.dp)
                .placeholder(true, 9.dp)
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun SurveyQuestionsContent(questions: List<QuestionContentUiModel>) {
    HorizontalPager(
        count = questions.size,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .statusBarsPadding()
            .padding(top = 68.dp, bottom = 110.dp)
            .fillMaxSize()
    ) { index ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppTheme.dimensions.mediumPadding)
        ) {
            Text(
                text = questions[index].order,
                color = Color.White.copy(alpha = 0.5f),
                style = Typography.body2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = questions[index].title,
                color = Color.White,
                style = Typography.h4
            )
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ) {
                // TODO: Add answer view here!
            }
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (nextButton) = createRefs()
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = null,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.constrainAs(nextButton) {
                bottom.linkTo(parent.bottom, margin = 54.dp)
                end.linkTo(parent.end, margin = 20.dp)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_survey_detail_arrow),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.size(56.dp),
            )
        }
    }
}

@Preview
@Composable
fun SurveyQuestionsScreenPreview(
    @PreviewParameter(LoadingParameterProvider::class) isLoading: Boolean
) {
    SurveyQuestionsScreenContent(
        SurveyQuestionsContentUiModel(
            isLoading = isLoading,
            backgroundUrl = "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l",
            questions = listOf(
                QuestionContentUiModel(
                    "1/2",
                    "What your name?"
                ),
                QuestionContentUiModel(
                    "2/2",
                    "How old are you?"
                )
            )
        )
    )
}
