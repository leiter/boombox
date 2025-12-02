package com.hitit.app.network

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ApiClient {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }

    suspend fun fetchSampleData(): String {
        return try {
            val response: HttpResponse = httpClient.get("https://httpbin.org/get")
            "Status: ${response.status}\n\nResponse received successfully!"
        } catch (e: Exception) {
            "Error fetching data: ${e.message}"
        }
    }

    fun close() {
        httpClient.close()
    }
}
