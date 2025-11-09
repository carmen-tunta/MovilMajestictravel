package com.mtg.usecases.bandeja

import com.mtg.data.BandejaRepository
import com.mtg.data.NetworkResult
import com.mtg.domain.Bandeja

class AtenderRequest(
    private val bandejaRepo: BandejaRepository
) {
    suspend fun invoke(id: String, token: String): NetworkResult<Bandeja> {
        return this.bandejaRepo.atenderRequest(id, token)
    }
}