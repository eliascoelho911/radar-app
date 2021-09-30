package com.github.eliascoelho911.radar.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.github.eliascoelho911.radar.ui.radar.RadarScreen
import com.github.eliascoelho911.radar.ui.radar.RadarViewModel
import com.github.eliascoelho911.radar.ui.theme.Fullscreen


class MainActivity : ComponentActivity() {

    private val radarViewModel: RadarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Fullscreen {
                RadarScreen(radarViewModel)
            }
        }
    }
}