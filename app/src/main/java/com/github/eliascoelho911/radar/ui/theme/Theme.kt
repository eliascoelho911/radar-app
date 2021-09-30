package com.github.eliascoelho911.radar.ui.theme

import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun Fullscreen(
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.apply {
        isNavigationBarVisible = false
        isStatusBarVisible = false
    }
    content()
}