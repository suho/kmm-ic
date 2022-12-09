package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.extension.placeholder
import co.nimblehq.ic.kmm.suv.android.ui.components.ErrorAlertDialog
import co.nimblehq.ic.kmm.suv.android.ui.components.ImageBackground
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import co.nimblehq.ic.kmm.suv.android.util.LoadingParameterProvider
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun SurveyQuestionsScreen(
    surveyQuestionsArgument: SurveyQuestionsArgument?,
    onCloseClick: () -> Unit,
    viewModel: SurveyQuestionsViewModel = getViewModel(),
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val coverImageUrl by viewModel.coverImageUrl.collectAsState()
    val questionUiModels by viewModel.questionContentUiModels.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSurveyDetail(surveyQuestionsArgument)
    }

    errorMessage?.let {
        ErrorAlertDialog(
            message = it,
            onButtonClick = viewModel::dismissError
        )
    }

    SurveyQuestionsScreenContent(
        SurveyQuestionsContentUiModel(
            isLoading = isLoading,
            backgroundUrl = coverImageUrl,
            questions = questionUiModels
        ),
        onCloseClick
    )
}

@Composable
private fun SurveyQuestionsScreenContent(
    uiModel: SurveyQuestionsContentUiModel,
    onCloseClick: () -> Unit = {}
) {
    ImageBackground(uiModel.backgroundUrl)
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
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        count = questions.size,
        verticalAlignment = Alignment.Top,
        state = pagerState,
        modifier = Modifier
            .statusBarsPadding()
            .padding(top = 68.dp, bottom = 110.dp)
            .fillMaxSize()
    ) { index ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
        val isLastPage = pagerState.currentPage == pagerState.pageCount - 1
        Button(
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isLastPage) Color.White else Color.Transparent
            ),
            elevation = null,
            contentPadding = PaddingValues(0.dp),
            shape = if (isLastPage) RoundedCornerShape(10.dp) else MaterialTheme.shapes.small,
            modifier = Modifier.constrainAs(nextButton) {
                bottom.linkTo(parent.bottom, margin = 54.dp)
                end.linkTo(parent.end, margin = 20.dp)
            }
        ) {
            if (isLastPage) {
                Text(
                    text = stringResource(id = R.string.submit_button),
                    color = Color.Black,
                    style = Typography.subtitle2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(120.dp)
                        .height(56.dp)
                        .wrapContentHeight()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_survey_detail_arrow),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.size(56.dp)
                )
            }
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
