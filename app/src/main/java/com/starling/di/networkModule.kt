package com.starling.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.starling.network.services.AccountsService
import com.starling.network.services.SavingsService
import com.starling.network.services.TransactionService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {

    single<Gson> { GsonBuilder().create() }
    single { provideOkHttpClient(androidContext()) }
    single { provideRetrofit(get(), get()) }
    single<AccountsService> { get<Retrofit>().create() }
    single<TransactionService> { get<Retrofit>().create() }
    single<SavingsService> { get<Retrofit>().create() }

}

fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {

    return Retrofit.Builder().baseUrl("https://api-sandbox.starlingbank.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
}

fun provideOkHttpClient(androidContext: Context): OkHttpClient {
    return OkHttpClientBuilder.createOkHttp(androidContext)
}