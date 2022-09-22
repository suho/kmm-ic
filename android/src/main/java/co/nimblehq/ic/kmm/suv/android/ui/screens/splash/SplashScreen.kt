package co.nimblehq.ic.kmm.suv.android.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.ic.kmm.suv.android.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onLaunch: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000L)
        onLaunch()
    }
    Box {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.bg_splash),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .matchParentSize()
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_logo_white),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen {}
}
