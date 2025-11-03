package com.ucb.data.bandeja

import com.ucb.data.NetworkResult
import com.ucb.domain.Bandeja

interface IBandejaRemoteDataSource {
    suspend fun getByAgent(
        agentId: String,
        token: String,
    ): NetworkResult<List<Bandeja>>

    suspend fun takeRequest(
        id: String,
        agentId: String,
        token: String,
    ): NetworkResult<Bandeja>
}
