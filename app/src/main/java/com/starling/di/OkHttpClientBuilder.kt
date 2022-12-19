package com.starling.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.starling.network.TokenInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpClientBuilder {

    fun createOkHttp(appContext: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        val collector = getChuckCollector(appContext)
        val interceptor = getInterceptor(appContext, collector)
        builder.addInterceptor(TokenInterceptor())
        builder.addInterceptor(interceptor)

        return builder.build()
    }
}

fun getChuckCollector(context: Context): ChuckerCollector {
    return ChuckerCollector(
        context,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )
}

fun getInterceptor(context: Context, collector: ChuckerCollector): ChuckerInterceptor {
    return ChuckerInterceptor.Builder(context)
        .collector(collector)
        .maxContentLength(250_000L)
        .alwaysReadResponseBody(true)
        .build()
}