package com.github.eliascoelho911.radar.ui.radar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

private val collisionPoints = mutableListOf<CollisionPoint>()

private const val collisionRange = 10

@Composable
fun RadarScreen(radarViewModel: RadarViewModel) {
    val data by radarViewModel.radarData().collectAsState(RadarResponse.getDefaultInstance())

    if (data.collisionDistance <= collisionRange)
        collisionPoints.add(CollisionPoint(data.collisionDistance, data.angDeg.toFloat()))

    if (data.angDeg == 0)
        collisionPoints.clear()

    Radar(scannerAngDeg = data.angDeg.toFloat(), collisionPoints)
}