package com.nutrition.calculator.di

import com.nutrition.calculator.BuildConfig
import com.nutrition.calculator.data.network.SupabaseClient
import com.nutrition.calculator.data.remote.api.ExternalApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun provideOkHttpClient(): OkHttpClient {
		val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
		return OkHttpClient.Builder()
			.addInterceptor(logging)
			.build()
	}

	@Provides
	@Singleton
	fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
		.baseUrl(BuildConfig.EXTERNAL_API_BASE_URL)
		.addConverterFactory(JacksonConverterFactory.create())
		.client(client)
		.build()

	@Provides
	@Singleton
	fun provideExternalApiServices(retrofit: Retrofit): ExternalApiServices =
		retrofit.create(ExternalApiServices::class.java)

	@Provides
	@Singleton
	fun provideSupabaseClient(): SupabaseClient = SupabaseClient()
}
