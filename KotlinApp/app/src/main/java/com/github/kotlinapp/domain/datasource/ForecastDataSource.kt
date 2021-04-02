package com.github.kotlinapp.domain.datasource

import com.github.kotlinapp.domain.model.Forecast
import com.github.kotlinapp.domain.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long):Forecast?
}