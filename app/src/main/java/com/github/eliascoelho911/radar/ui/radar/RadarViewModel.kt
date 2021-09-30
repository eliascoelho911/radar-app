package com.github.eliascoelho911.radar.ui.radar

import androidx.lifecycle.ViewModel
import com.github.eliascoelho911.radar.data.radar.RadarRepository

class RadarViewModel : ViewModel() {
    private val radarRepository = RadarRepository()

    fun radarData() = radarRepository.data()
}