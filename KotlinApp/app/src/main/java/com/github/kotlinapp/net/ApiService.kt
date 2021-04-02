package com.github.kotlinapp.net

import com.github.kotlinapp.data.server.ForecastResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface ApiService {


    @GET("data/2.5/forecast/daily?mode=json&units=metric&cnt=7")
    @FormUrlEncoded
    fun getWeatherData(
        @Field("APPID") appId: String,
        @Field("zip") zip: String
    ): Observable<ForecastResult>
}