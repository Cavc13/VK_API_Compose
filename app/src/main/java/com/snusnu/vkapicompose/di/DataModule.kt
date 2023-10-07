package com.snusnu.vkapicompose.di

import android.content.Context
import com.snusnu.vkapicompose.data.network.ApiFactory
import com.snusnu.vkapicompose.data.network.ApiService
import com.snusnu.vkapicompose.data.repository.NewsFeedRepositoryImpl
import com.snusnu.vkapicompose.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    companion object{
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @ApplicationScope
        @Provides
        fun provideVkStorage(
            context: Context
        ): VKPreferencesKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }
    }
}