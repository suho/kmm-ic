package co.nimblehq.ic.kmm.suv.android.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class Dimensions(mediumPadding: Dp) {
    var mediumPadding by mutableStateOf(mediumPadding, structuralEqualityPolicy())
        internal set
}

fun appDimensions(mediumPadding: Dp = 20.dp): Dimensions = Dimensions(mediumPadding)

internal val LocalDimensions = staticCompositionLocalOf { appDimensions() }
