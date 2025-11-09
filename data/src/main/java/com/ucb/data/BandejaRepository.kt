package com.ucb.data

import com.ucb.data.bandeja.IBandejaRemoteDataSource
import com.ucb.domain.Bandeja

class BandejaRepository(
    private val remoteDS: IBandejaRemoteDataSource,
) {
    suspend fun getByAgent(
        agentId: String,
        token: String,
    ): NetworkResult<List<Bandeja>> = this.remoteDS.getByAgent(agentId, token)

    suspend fun takeRequest(
        id: String,
        agentId: String,
        token: String,
    ): NetworkResult<Bandeja> = this.remoteDS.takeRequest(id, agentId, token)

    suspend fun releaseRequest(
        id: String,
        agentId: String,
        token: String,
    ): NetworkResult<Bandeja> = this.remoteDS.releaseRequest(id, agentId, token)

    suspend fun cotizandoRequest(
        id: String,
        agentId: String,
        token: String,
    ): NetworkResult<Bandeja> = this.remoteDS.cotizandoRequest(id, agentId, token)

    suspend fun requestSinRespuesta(
        id: String,
        agentId: String,
        token: String,
    ): NetworkResult<Bandeja> = this.remoteDS.requestSinRespuesta(id, agentId, token)

    suspend fun atenderRequest(
        id: String,
        token: String,
    ): NetworkResult<Bandeja> = this.remoteDS.atenderRequest(id, token)
}
