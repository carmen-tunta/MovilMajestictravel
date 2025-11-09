package com.mtg.usecases.bandeja

import com.mtg.data.BandejaRepository
import com.mtg.data.NetworkResult
import com.mtg.domain.Bandeja

class GetBandejaByAgent(
    private val bandejaRepo: BandejaRepository
) {
    suspend fun invoke(agentId: String, token: String): NetworkResult<List<Bandeja>> {
        return bandejaRepo.getByAgent(agentId, token)
    }
}
