package com.example.model

data class StartSessionRequest(
    val station_id: String,
    val driver_token: String,
    val callback_url: String
) {
    fun isValid(): Boolean {
        val regex = Regex("^[A-Za-z0-9._~-]{20,80}$")
        return station_id.isNotBlank() &&
               callback_url.startsWith("http") &&
               regex.matches(driver_token)
    }
}
