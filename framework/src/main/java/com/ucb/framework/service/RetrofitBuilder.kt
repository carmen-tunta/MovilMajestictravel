package com.ucb.framework.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class RetrofitBuilder(
    val context: Context
) {

    private fun getRetrofit(url: String): Retrofit {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(url)
            .client(client)
            .build()
    }
    val apiService: IApiService = getRetrofit(BASE_URL).create(IApiService::class.java)
    val movieService: IMovieApiService = getRetrofit(BASE_URL_MOVIES).create(IMovieApiService::class.java)
    val mtgApiService: IMTGApiService = getRetrofit(BASE_URL_MTG).create(IMTGApiService::class.java)

    companion object {
        private const val BASE_URL = "https://api.github.com"
        private const val BASE_URL_MOVIES = "https://api.themoviedb.org"
        private const val BASE_URL_MTG = "http://192.168.1.71:3080/"
    }
}
