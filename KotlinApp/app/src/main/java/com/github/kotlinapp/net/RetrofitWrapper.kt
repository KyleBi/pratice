package com.github.kotlinapp.net

import android.util.Log
import com.github.kotlinapp.App
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络接口服务包装类
 *
 * @author bps
 */
class RetrofitWrapper {
    fun <T> getNetService(clazz: Class<T>): T {
        val endpoint = "http://api.openweathermap.org/"
        return getNetService(clazz, endpoint)
    }

    private fun <T> getNetService(clazz: Class<T>, endPoint: String): T {
        val cacheSize = 50 * 1024 * 1024
        val cache = Cache(App.instance.cacheDir, cacheSize.toLong())
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .serializeNulls()
            .setLenient()
            .create()
        val interceptor = Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
                .newBuilder()
                .build()
            chain.proceed(request)
        }
        val httpLoggingInterceptor =
            HttpLoggingInterceptor { message: String -> Log.i("retrofit", "log: $message") }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .cache(cache)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(endPoint)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(clazz)
    }

    companion object {
        private var instance: RetrofitWrapper? = null
        fun getInstance(): RetrofitWrapper {
            instance = RetrofitWrapper()
            return instance!!
        }
    }

}