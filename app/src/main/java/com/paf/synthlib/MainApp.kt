package com.paf.synthlib

import android.app.Application
import com.paf.synthlib.di.dbModule
import com.paf.synthlib.di.presetModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(dbModule, presetModule)
        }
    }
}