package com.github.kotlinapp.domain.commands

import com.github.kotlinapp.domain.datasource.ForecastProvider
import com.github.kotlinapp.domain.model.ForecastList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RequestForecastCommand(
    private val zipCode: Long,
    private val forecastProvider: ForecastProvider = ForecastProvider()
) : Command<ForecastList> {

    companion object {
        const val DAYS = 7
    }

    override fun execute() = forecastProvider.requestByZipCode(zipCode, DAYS)
}