package com.github.kotlinapp.data.server

import android.util.Log
import com.github.kotlinapp.data.db.ForecastDb
import com.github.kotlinapp.domain.datasource.ForecastDataSource
import com.github.kotlinapp.domain.model.Forecast
import com.github.kotlinapp.domain.model.ForecastList

class ForecastServer(
    private val dataMap: ServerDataMap = ServerDataMap(),
    private val db: ForecastDb = ForecastDb()
) : ForecastDataSource {


    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val forecastList = dataMap.convertToDomain(zipCode, result)
        db.saveForecast(forecastList)
        return db.requestForecastByZipCode(zipCode, date)
    }

    override fun requestDayForecast(id: Long) = throw UnsupportedOperationException()
}