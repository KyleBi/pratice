package com.github.kotlinapp.domain.datasource

import com.github.kotlinapp.data.db.ForecastDb
import com.github.kotlinapp.data.server.ForecastServer
import com.github.kotlinapp.domain.model.ForecastList
import com.github.kotlinapp.extensions.firstResult

class ForecastProvider(private val sources: List<ForecastDataSource> = SOURCE) {


    companion object {
        const val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCE = listOf(ForecastDb(), ForecastServer())
    }


    fun requestByZipCode(zipCode: Long, days: Int) = requestToSources {
        val result = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (result != null && result.size >= days) result else null
    }


    fun requestForecast(id: Long) = requestToSources { it.requestDayForecast(id) }


    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T =
        sources.firstResult { f(it) }
}