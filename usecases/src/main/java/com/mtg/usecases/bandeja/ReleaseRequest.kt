package com.mtg.usecases.bandeja

import com.mtg.data.BandejaRepository
import com.mtg.data.NetworkResult
import com.mtg.domain.Bandeja

class ReleaseRequest (
    private val bandejaRepo: BandejaRepository
) {
    suspend fun invoke(id: String, agentId: String, token: String): NetworkResult<Bandeja> {
        return this.bandejaRepo.releaseRequest(id, agentId, token)
    }
}