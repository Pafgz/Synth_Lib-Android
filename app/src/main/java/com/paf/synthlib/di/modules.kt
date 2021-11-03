package com.paf.synthlib.di

import android.app.Application
import androidx.room.Room
import com.paf.synthlib.domain.preset.PresetInteractor
import com.paf.synthlib.domain.preset.PresetRepository
import com.paf.synthlib.implementation.database.AppDatabase
import com.paf.synthlib.implementation.database.preset.PresetDao
import com.paf.synthlib.implementation.database.preset.TagDao
import com.paf.synthlib.implementation.preset.PresetInteractorImpl
import com.paf.synthlib.implementation.preset.PresetRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val presetModule = module {
    fun providePresetRepository(presetDao: PresetDao): PresetRepository =
        PresetRepositoryImpl(presetDao)

    fun providePresetInteractor(presetRepository: PresetRepository): PresetInteractor =
        PresetInteractorImpl(presetRepository)

    single { providePresetRepository(get()) }

    single { providePresetInteractor(get()) }
}

val dbModule = module {
    fun provideDataBase(application: Application): AppDatabase =
        AppDatabase.getInstance(application.applicationContext)

    fun providePresetDao(dataBase: AppDatabase): PresetDao = dataBase.presetDao()

    fun provideTagDao(dataBase: AppDatabase): TagDao = dataBase.tagDao()

    single { provideDataBase(androidApplication()) }

    single { providePresetDao(get()) }

    single { provideTagDao(get()) }
}
