package co.nimblehq.ic.kmm.suv.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import co.nimblehq.ic.kmm.suv.android.ui.navigation.NavGraph
import co.nimblehq.ic.kmm.suv.android.ui.theme.MyApplicationTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyApplicationTheme {
                NavGraph()
            }
        }
    }
}
