package com.github.eliascoelho911.radar.data.radar

class RadarRepository {
    private val radarGrpc = RadarDataSource()

    fun data() = radarGrpc.data()
}