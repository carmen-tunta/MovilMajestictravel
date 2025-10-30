package com.ucb.data

import com.ucb.data.bandeja.IBandejaRemoteDataSource
import com.ucb.domain.Bandeja

class BandejaRepository(
    private val remoteDS: IBandejaRemoteDataSource,
) {
    suspend fun getByAgent(
        agentId: String,
        token: String,
    ) : NetworkResult<List<Bandeja>> = this.remoteDS.getByAgent(agentId, token)
}
