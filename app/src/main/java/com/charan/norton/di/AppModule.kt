package com.charan.norton.di

import com.anthropic.client.AnthropicClient
import com.anthropic.client.okhttp.AnthropicOkHttpClient
import com.charan.norton.BuildConfig
import com.charan.norton.features.genie.data.repository.GenieRepositoryImpl
import com.charan.norton.features.genie.domain.repository.GenieRepository
import com.charan.norton.common.network.AndroidNetworkChecker
import com.charan.norton.common.network.NetworkChecker
import com.charan.norton.features.scan.data.repository.ScanRepositoryImpl
import com.charan.norton.features.scan.domain.repository.ScanRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    /**
     * Binds GenieRepositoryImpl as the singleton implementation of GenieRepository.
     */
    @Binds
    @Singleton
    abstract fun bindGenieRepository(impl: GenieRepositoryImpl): GenieRepository

    /**
     * Binds ScanRepositoryImpl as the singleton implementation of ScanRepository.
     */
    @Binds
    @Singleton
    abstract fun bindScanRepository(impl: ScanRepositoryImpl): ScanRepository

    /**
     * Binds AndroidNetworkChecker as the singleton implementation of NetworkChecker.
     */
    @Binds
    @Singleton
    abstract fun bindNetworkChecker(impl: AndroidNetworkChecker): NetworkChecker

    companion object {

        /**
         * Provides a singleton AnthropicClient configured with the API key from BuildConfig.
         */
        @Provides
        @Singleton
        fun provideAnthropicClient(): AnthropicClient = AnthropicOkHttpClient.builder()
            .apiKey(BuildConfig.ANTHROPIC_API_KEY)
            .build()
    }
}