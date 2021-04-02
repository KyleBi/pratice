package com.github.kotlinapp.domain.commands

import com.github.kotlinapp.domain.datasource.ForecastProvider
import com.github.kotlinapp.domain.model.Forecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RequestDayForecastCommand(
    val id: Long,
    private val forecastProvider: ForecastProvider = ForecastProvider()
) : Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)

}