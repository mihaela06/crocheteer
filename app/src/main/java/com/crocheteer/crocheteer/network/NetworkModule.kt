package com.crocheteer.crocheteer.network

import android.content.Context
import com.crocheteer.crocheteer.R
import com.crocheteer.crocheteer.network.api.AuthInterceptor
import com.crocheteer.crocheteer.network.api.YarnApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideRavelryApiService(@ApplicationContext context: Context): YarnApiService =
        createRavelryApiServiceWithRetrofit(
            context.getString(R.string.ravelry_user),
            context.getString(R.string.ravelry_password),
        )

    @Provides
    @Singleton
    fun provideRavelrySearchRepository(@ApplicationContext context: Context)
            : YarnSearchRepository = RavelryYarnSearchRepository(provideRavelryApiService(context))

    companion object {
        private const val ravelryApiUrl = "https://api.ravelry.com/"

        fun createRavelryApiServiceWithRetrofit(user: String, password: String): YarnApiService =
            Retrofit.Builder().baseUrl(ravelryApiUrl)
                .addConverterFactory(GsonConverterFactory.create()).client(
                    OkHttpClient.Builder().addInterceptor(
                        AuthInterceptor(
                            user, password
                        )
                    ).build()
                ).build().create(YarnApiService::class.java)
    }
}