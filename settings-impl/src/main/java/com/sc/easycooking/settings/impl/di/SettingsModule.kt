package com.sc.easycooking.settings.impl.di

import android.content.Context
import com.sc.easycooking.settings.api.domain.SettingsInteractor
import com.sc.easycooking.settings.impl.domain.SettingsInteractorImpl
import com.sc.easycooking.settings.impl.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class, ViewModelComponent::class)
internal abstract class SettingsModule {

    @Binds
    abstract fun bindSettingsInteractor(
        settingsInteractor: SettingsInteractorImpl
    ): SettingsInteractor
}

@Module
@InstallIn(SingletonComponent::class)
internal object SettingsRepositoryModule {

    @Provides
    @Singleton
    fun bindSettingsRepository(
        @ApplicationContext context: Context,
    ): SettingsRepository {
        return SettingsRepository(context)
    }
}