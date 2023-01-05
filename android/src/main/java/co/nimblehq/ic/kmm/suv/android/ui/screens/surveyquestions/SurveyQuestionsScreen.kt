package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.extension.placeholder
import co.nimblehq.ic.kmm.suv.android.ui.components.*
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import co.nimblehq.ic.kmm.suv.android.util.LoadingParameterProvider
import co.nimblehq.ic.kmm.suv.domain.model.AnswerInput
import co.nimblehq.ic.kmm.suv.domain.model.QuestionAndAnswers
import co.nimblehq.ic.kmm.suv.domain.model.QuestionDisplayType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun SurveyQuestionsScreen(
    surveyQuestionsArgument: SurveyQuestionsArgument?,
    onCloseScreenClick: () -> Unit,
    onSubmitSuccess: () -> Unit,
    viewModel: SurveyQuestionsViewModel = getViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val coverImageUrl by viewModel.coverImageUrl.collectAsState()
    val questionUiModels by viewModel.questionContentUiModels.collectAsState()
    val submittingAnswerState by viewModel.submittingAnswerState.collectAsState()

    var showExitDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadSurveyDetail(surveyQuestionsArgument)
    }

    LaunchedEffect(submittingAnswerState) {
        if (submittingAnswerState == SubmittingAnswerState.SUCCESS) {
            onSubmitSuccess()
        }
    }

    errorMessage?.let {
        ErrorAlertDialog(
            message = it,
            onButtonClick = viewModel::dismissError
        )
    }

    if (showExitDialog) {
        ExitAlertDialog(
            onConfirmClick = {
                showExitDialog = false
                onCloseScreenClick()
            },
            onDismissClick = {
                showExitDialog = false
            }
        )
    }

    SurveyQuestionsScreenContent(
        SurveyQuestionsContentUiModel(
            isLoading = isLoading,
            backgroundUrl = coverImageUrl,
            questions = questionUiModels
        ),
        onCloseButtonClick = {
            showExitDialog = true
        },
        onAnswerChange = viewModel::answerQuestion,
        onAnswersSubmit = viewModel::submitSurveyResponse,
        isSubmitting = submittingAnswerState == SubmittingAnswerState.SUBMITTING
    )
}

@Composable
private fun SurveyQuestionsScreenContent(
    uiModel: SurveyQuestionsContentUiModel,
    onCloseButtonClick: () -> Unit = {},
    onAnswerChange: (QuestionAndAnswers) -> Unit,
    onAnswersSubmit: () -> Unit,
    isSubmitting: Boolean
) {
    ImageBackground(uiModel.backgroundUrl, isBlur = true)
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
    ) {
        Button(
            onClick = onCloseButtonClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = null,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            modifier = Modifier
                .semantics {
                    contentDescription = SurveyQuestionsContentDescription.BUTTON_CLOSE
                }
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
        SurveyQuestionsContent(uiModel.questions, onAnswerChange, onAnswersSubmit, isSubmitting)
    }
}

@Composable
private fun ExitAlertDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        title = {
            Text(
                text = stringResource(R.string.exit_warning_title),
                style = Typography.subtitle2
            )
        },
        text = {
            Text(
                text = stringResource(R.string.exit_detail_message),
                style = Typography.overline
            )
        },
        backgroundColor = Color.White,
        confirmButton = {
            Button(
                onClick = onConfirmClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black
                ),
            ) {
                Text(
                    text = stringResource(R.string.yes),
                    color = Color.White,
                    style = Typography.overline
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                ),
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = Color.Black,
                    style = Typography.caption
                )
            }
        }
    )
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
private fun SurveyQuestionsContent(
    questionUiModels: List<QuestionContentUiModel>,
    onAnswerChange: (QuestionAndAnswers) -> Unit,
    onAnswersSubmit: () -> Unit,
    isSubmitting: Boolean
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        count = questionUiModels.size,
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
            val uiModel = questionUiModels[index]
            Text(
                text = uiModel.progress,
                color = Color.White.copy(alpha = 0.5f),
                style = Typography.body2,
                modifier = Modifier
                    .semantics {
                        contentDescription = SurveyQuestionsContentDescription.progressLabel(index)
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = uiModel.title,
                color = Color.White,
                style = Typography.h4,
                modifier = Modifier
                    .semantics {
                        contentDescription =
                            SurveyQuestionsContentDescription.questionTitleLabel(index)
                    }
            )
            Spacer(modifier = Modifier.height(80.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Answer(
                    type = uiModel.displayType,
                    onAnswersChange = {
                        onAnswerChange(
                            QuestionAndAnswers(
                                questionId = uiModel.id,
                                answerInputs = it
                            )
                        )
                    },
                    questionIndex = index
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (nextButton) = createRefs()
        val isLastPage = pagerState.currentPage == pagerState.pageCount - 1
        Button(
            onClick = {
                if (isLastPage) {
                    onAnswersSubmit()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isLastPage && !isSubmitting) Color.White else Color.Transparent
            ),
            elevation = null,
            enabled = !isSubmitting,
            contentPadding = PaddingValues(0.dp),
            shape = if (isLastPage) AppTheme.shapes.medium else AppTheme.shapes.small,
            modifier = Modifier
                .constrainAs(nextButton) {
                    bottom.linkTo(parent.bottom, margin = 54.dp)
                    end.linkTo(parent.end, margin = 20.dp)
                }
                .semantics {
                    contentDescription = SurveyQuestionsContentDescription.BUTTON_NEXT_OR_SUBMIT
                }
        ) {
            if (isSubmitting) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(AppTheme.dimensions.defaultComponentHeight)
                        .background(Color.White)
                        .clip(CircleShape)
                ) {
                    CircularProgressIndicator(color = Color.Black)
                }
            } else if (isLastPage) {
                Text(
                    text = stringResource(id = R.string.submit_button),
                    color = Color.Black,
                    style = Typography.subtitle2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(AppTheme.dimensions.defaultComponentHeight)
                        .wrapContentHeight()
                        .padding(horizontal = AppTheme.dimensions.mediumPadding)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_survey_detail_arrow),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.size(AppTheme.dimensions.defaultComponentHeight)
                )
            }
        }
    }
}

@Composable
private fun Answer(
    type: QuestionDisplayType,
    onAnswersChange: (List<AnswerInput>) -> Unit,
    questionIndex: Int = 0 // TODO: For UITest, improve later
) {
    when (type) {
        is QuestionDisplayType.Star -> EmojiRatingAnswer(
            emojis = List(5) {
                { Emoji(name = EMOJI_STAR) }
            },
            answers = type.answers,
            onInputChange = {
                onAnswersChange(listOf(it))
            },
            input = type.input.elementAtOrNull(0),
            questionIndex = questionIndex
        )
        is QuestionDisplayType.Heart -> EmojiRatingAnswer(
            emojis = List(5) {
                { Emoji(name = EMOJI_HEART) }
            },
            answers = type.answers,
            onInputChange = {
                onAnswersChange(listOf(it))
            },
            input = type.input.elementAtOrNull(0),
            questionIndex = questionIndex
        )
        is QuestionDisplayType.Smiley -> EmojiRatingAnswer(
            emojis = listOf(
                EMOJI_POUTING_FACE,
                EMOJI_CONFUSED_FACE,
                EMOJI_NEUTRAL_FACE,
                EMOJI_SLIGHTLY_SMILING_FACE,
                EMOJI_GRINNING_FACE_WITH_SMILING_EYES
            ).map { { Emoji(name = it) } },
            highlightStyle = EmojiHighlightStyle.ONE,
            answers = type.answers,
            onInputChange = {
                onAnswersChange(listOf(it))
            },
            input = type.input.elementAtOrNull(0),
            questionIndex = questionIndex
        )
        is QuestionDisplayType.Nps -> NpsAnswer(
            answers = type.answers,
            onInputChange = {
                onAnswersChange(listOf(it))
            },
            input = type.input.elementAtOrNull(0)
        )
        is QuestionDisplayType.Choice -> MultipleChoicesAnswer(
            answers = type.answers,
            onInputChange = {
                onAnswersChange(it.toList())
            },
            input = type.input.toSet()
        )
        is QuestionDisplayType.Textarea -> TextareaAnswer(
            answer = type.answers.first(),
            onInputChange = {
                onAnswersChange(listOf(it))
            },
            input = type.input.elementAtOrNull(0),
            modifier = Modifier.height(170.dp)
        )
        is QuestionDisplayType.Textfield -> FormAnswer(
            answers = type.answers,
            onInputChange = {
                onAnswersChange(it)
            },
            input = type.input
        )
        is QuestionDisplayType.Dropdown -> DropdownAnswer(
            answers = type.answers,
            onInputChange = {
                onAnswersChange(listOf(it))
            },
            input = type.input.elementAtOrNull(0)
        )
        else -> Unit
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
                    progress = "1/2",
                    id = "1",
                    title = "What your name?",
                    displayType = QuestionDisplayType.Intro
                ),
                QuestionContentUiModel(
                    progress = "2/2",
                    id = "2",
                    title = "How old are you?",
                    displayType = QuestionDisplayType.Intro
                )
            )
        ),
        onAnswerChange = {},
        onAnswersSubmit = {},
        isSubmitting = true
    )
}
