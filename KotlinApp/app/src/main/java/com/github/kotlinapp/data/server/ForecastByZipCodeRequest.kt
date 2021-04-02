package com.github.kotlinapp.data.server

import android.util.Log
import com.google.gson.Gson
import java.net.URL

class ForecastByZipCodeRequest(private val zipCode: Long) {
    companion object {
        private const val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private const val URL =
            "https://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"
        private const val COMPLETE_URL = "$URL&APPID=$APP_ID&zip="
    }

    fun execute(): ForecastResult {
        val readText = URL(COMPLETE_URL + zipCode).readText()
        Log.i("HelloWorld", "execute: $readText")
        return Gson().fromJson(readText, ForecastResult::class.java)
    }

}