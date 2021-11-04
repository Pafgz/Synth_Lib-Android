package com.paf.synthlib.di

import android.app.Application
import android.content.Context
import com.paf.synthlib.domain.preset.PresetInteractor
import com.paf.synthlib.domain.preset.PresetRepository
import com.paf.synthlib.implementation.database.AppDatabase
import com.paf.synthlib.implementation.database.preset.PresetDao
import com.paf.synthlib.implementation.database.preset.TagDao
import com.paf.synthlib.implementation.preset.PresetInteractorImpl
import com.paf.synthlib.implementation.preset.PresetRepositoryImpl
import com.paf.synthlib.utils.FileManager
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val presetModule = module {
    fun providePresetRepository(presetDao: PresetDao, fileManager: FileManager): PresetRepository =
        PresetRepositoryImpl(presetDao, fileManager)

    fun providePresetInteractor(presetRepository: PresetRepository): PresetInteractor =
        PresetInteractorImpl(presetRepository)

    single { providePresetRepository(get(), get()) }

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

val storageModule = module {
    fun provideFileManager(context: Context): FileManager = FileManager(context)

    single { provideFileManager(androidContext()) }
}