package com.github.kotlinapp.data.server

import com.github.kotlinapp.domain.model.Forecast as ModelForecast
import com.github.kotlinapp.domain.model.ForecastList
import java.util.*
import java.util.concurrent.TimeUnit

class ServerDataMap {

    fun convertToDomain(zipCode: Long, forecast: ForecastResult) = with(forecast) {
        ForecastList(
            zipCode,
            city.name,
            city.country,
            convertForecastListToDomain(list)
        )
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.mapIndexed { index, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(index.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }
    }


    private fun convertForecastItemToDomain(forecast: Forecast) = with(forecast) {
        ModelForecast(
          -1,
            dt,
            weather[0].description,
            temp.max.toInt(),
            temp.min.toInt(),
            generateIconUrl(weather[0].icon)
        )
    }

    private fun generateIconUrl(iconCode: String): String =
        "http://openweathermap.org/img/w/$iconCode.png"
}