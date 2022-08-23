package co.nimblehq.suvkmmsurveys.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import co.nimblehq.suv.kmm.shared.Greeting
import co.nimblehq.suvkmmsurveys.ui.screens.HomeScreen
import co.nimblehq.suvkmmsurveys.ui.theme.Theme
import dagger.hilt.android.AndroidEntryPoint

// TODO: Consider update BaseActivity to extends ComponentActivity.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                HomeScreen(title = Greeting().greeting())
            }
        }
    }
}