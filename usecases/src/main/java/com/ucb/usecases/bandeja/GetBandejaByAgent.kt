package com.ucb.usecases.bandeja

import com.ucb.data.BandejaRepository
import com.ucb.data.NetworkResult
import com.ucb.domain.Bandeja

class GetBandejaByAgent(
    private val bandejaRepo: BandejaRepository
) {
    suspend fun invoke(agentId: String, token: String): NetworkResult<List<Bandeja>> {
        return bandejaRepo.getByAgent(agentId, token)
    }
}
