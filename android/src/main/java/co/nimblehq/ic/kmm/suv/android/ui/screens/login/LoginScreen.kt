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
import androidx.compose.ui.draw.alpha
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
import co.nimblehq.ic.kmm.suv.android.ui.components.PrimaryButton
import co.nimblehq.ic.kmm.suv.android.ui.components.PrimaryTextField
import kotlinx.coroutines.delay

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var componentsVisible by remember {  mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        delay(4000)
        componentsVisible = true
    }

    Box {
        Box(modifier = Modifier.fillMaxSize()) {
            BackgroundImage(modifier = Modifier.matchParentSize())
        }
        NimbleLogo(
            modifier = Modifier.align(Alignment.Center)
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
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                PrimaryTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = stringResource(id = R.string.login_email),
                )
                PrimaryTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = stringResource(id = R.string.login_password),
                    visualTransformation = PasswordVisualTransformation(),
                )
                PrimaryButton(
                    title = stringResource(id = R.string.login_button),
                    onClick = { /* TODO: Handle in integrate task */ },
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
    LaunchedEffect(key1 = true) {
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
    var visible by remember {  mutableStateOf(false) }
    var offsetState by remember {  mutableStateOf(Offset(0f, 0f))  }
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

    LaunchedEffect(key1 = true) {
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
fun DefaultPreview() {
    LoginScreen()
}
