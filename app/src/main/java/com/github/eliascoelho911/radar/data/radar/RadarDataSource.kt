package com.github.eliascoelho911.radar.data.radar

import RadarGrpcKt
import android.content.Context
import com.github.eliascoelho911.radar.R
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.net.URL
import java.util.logging.Logger

class RadarDataSource {
    private val logger = Logger.getLogger(RadarDataSource::class.java.simpleName)

    private val radarStub by lazy { RadarGrpcKt.RadarCoroutineStub(channel()) }

    fun data() = radarStub.data(RadarRequest.getDefaultInstance())

    private fun channel(): ManagedChannel {
        val url = URL("http://192.168.0.135:50051")

        logger.info("Connecting to ${url.host}:${url.port}")

        val builder = ManagedChannelBuilder.forAddress(url.host, url.port)
        builder.usePlaintext()

        return builder.executor(Dispatchers.IO.asExecutor()).build()
    }
}