package com.app.santasgifts.data

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModel{

    @Provides
    @Singleton
    fun provideRepository(app: Application): Repository{
        return RepositoryImpl(app)
    }
}
