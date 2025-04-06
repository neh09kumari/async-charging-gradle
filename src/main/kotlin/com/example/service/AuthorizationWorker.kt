package com.example.service

import com.example.model.AuthDecision
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import io.ktor.serialization.jackson.*

object AuthorizationWorker {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson()
        }
    }

    fun start() = CoroutineScope(Dispatchers.IO).launch {
        for (request in AuthorizationQueue.queue) {
            delay(1000) // simulate processing

            val status = when {
                request.driver_token.startsWith("valid") -> "allowed"
                request.driver_token.startsWith("invalid") -> "not_allowed"
                else -> "unknown"
            }

            val decision = AuthDecision(
                station_id = request.station_id,
                driver_token = request.driver_token,
                status = status
            )

            try {
                val response: HttpResponse = client.post(request.callback_url) {
                    contentType(ContentType.Application.Json)
                    setBody(decision)
                }
                println("Callback sent to ${request.callback_url}, response: ${response.status}")
            } catch (e: Exception) {
                println("Failed to send callback to ${request.callback_url}: ${e.message}")
            }
        }
    }
}
