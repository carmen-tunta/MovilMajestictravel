package com.ucb.usecases.bandeja

import com.ucb.data.BandejaRepository
import com.ucb.data.NetworkResult
import com.ucb.domain.Bandeja

class TakeRequest (
    private val bandejaRepo: BandejaRepository
) {
    suspend fun invoke(id: String, idAgent: String, token: String): NetworkResult<Bandeja> {
        return this.bandejaRepo.takeRequest(id, idAgent, token)
    }
}