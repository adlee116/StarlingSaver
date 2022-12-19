package com.starling

import android.app.Application
import com.starling.di.networkModule
import com.starling.di.repositoryModule
import com.starling.di.useCaseModule
import com.starling.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StarlingSaver: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StarlingSaver)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    useCaseModule,
                    networkModule
                )
            )
        }
    }
}