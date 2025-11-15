package com.mtg.framework.service

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
    val mtgApiService: IMTGApiService = getRetrofit(BASE_URL_MTG).create(IMTGApiService::class.java)

    companion object {
        private const val BASE_URL_MTG = "https://apis.majestictravelgroup.com/"
    }
}
