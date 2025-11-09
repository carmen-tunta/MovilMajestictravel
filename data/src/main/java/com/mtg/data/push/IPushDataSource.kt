package com.mtg.data.push

interface IPushDataSource {
    suspend fun getToken(): String
}