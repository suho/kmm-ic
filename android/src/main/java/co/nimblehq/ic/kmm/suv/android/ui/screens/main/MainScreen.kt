package co.nimblehq.ic.kmm.suv.android.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import co.nimblehq.ic.kmm.suv.Greeting

@Composable
fun MainScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = Greeting().greeting(),
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black
        )
    }
}
