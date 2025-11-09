package com.ucb.usecases.bandeja

import com.ucb.data.BandejaRepository
import com.ucb.data.NetworkResult
import com.ucb.domain.Bandeja

class AtenderRequest(
    private val bandejaRepo: BandejaRepository
) {
    suspend fun invoke(id: String, token: String): NetworkResult<Bandeja> {
        return this.bandejaRepo.atenderRequest(id, token)
    }
}