package co.nimblehq.ic.kmm.suv.android.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.ui.components.ErrorAlertDialog
import co.nimblehq.ic.kmm.suv.android.ui.components.PrimaryButton
import co.nimblehq.ic.kmm.suv.android.ui.components.PrimaryTextField
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    onLogInSuccess: () -> Unit,
    viewModel: LoginViewModel = getViewModel(),
    defaultComponentsVisible: Boolean = false
) {
    var componentsVisible by remember { mutableStateOf(defaultComponentsVisible) }
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLoginSuccess by viewModel.isLoginSuccess.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        delay(4000)
        componentsVisible = true
    }

    errorMessage?.let {
        ErrorAlertDialog(
            message = it,
            onButtonClick = { viewModel.dismissError() }
        )
    }

    LaunchedEffect(isLoginSuccess) {
        if (isLoginSuccess) {
            onLogInSuccess()
        }
    }

    LoginScreenContent(
        email = email,
        password = password,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onLogInClick = viewModel::logIn,
        isLoading = isLoading,
        componentsVisible = componentsVisible
    )
}

@Composable
private fun LoginScreenContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogInClick: () -> Unit,
    isLoading: Boolean = false,
    componentsVisible: Boolean = false,
) {
    Box {
        Box(modifier = Modifier.fillMaxSize()) {
            BackgroundImage(modifier = Modifier.matchParentSize())
        }
        NimbleLogo(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentHeight()
                .navigationBarsPadding()
                .imePadding()
        )

        AnimatedVisibility(
            visible = componentsVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.mediumPadding),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                PrimaryTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    placeholder = stringResource(id = R.string.login_email),
                )
                PrimaryTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    placeholder = stringResource(id = R.string.login_password),
                    visualTransformation = PasswordVisualTransformation(),
                )
                PrimaryButton(
                    text = stringResource(id = R.string.login_button),
                    isLoading = isLoading,
                    onClick = onLogInClick,
                )
            }
        }
    }
}

@Composable
private fun BackgroundImage(modifier: Modifier) {
    var blurState by remember { mutableStateOf(0f) }
    val animateBlurState by animateFloatAsState(
        targetValue = blurState,
        tween(
            durationMillis = 500,
            delayMillis = 500
        )
    )
    LaunchedEffect(Unit) {
        delay(3000)
        blurState = 20f
    }
    Image(
        painter = painterResource(id = R.drawable.bg_splash),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
            .blur(radius = animateBlurState.dp)
    )
}

@Composable
private fun NimbleLogo(modifier: Modifier = Modifier) {
    var visible by remember { mutableStateOf(false) }
    var offsetState by remember { mutableStateOf(Offset(0f, 0f)) }
    var scaleLogoState by remember { mutableStateOf(1f) }
    val animateOffset by animateOffsetAsState(
        targetValue = offsetState,
        tween(
            durationMillis = 500,
            delayMillis = 500
        )
    )
    val animateScaleLogo by animateFloatAsState(
        targetValue = scaleLogoState,
        tween(
            durationMillis = 500,
            delayMillis = 500
        )
    )
    LaunchedEffect(Unit) {
        delay(2000)
        visible = true
        delay(1000)
        offsetState = Offset(0f, -229f)
        scaleLogoState = 1.0f / 1.2f
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo_white),
            contentDescription = null,
            modifier = modifier
                .offset(animateOffset.x.dp, animateOffset.y.dp)
                .scale(animateScaleLogo)
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreenContent("", "", {}, {}, {}, componentsVisible = true)
}
