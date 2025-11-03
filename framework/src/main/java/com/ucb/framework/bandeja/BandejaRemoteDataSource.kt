package com.ucb.framework.bandeja

import com.ucb.data.NetworkResult
import com.ucb.data.bandeja.IBandejaRemoteDataSource
import com.ucb.domain.Bandeja
import com.ucb.framework.mappers.toModel
import com.ucb.framework.service.RetrofitBuilder
import com.ucb.framework.service.TakeRequestBody
import java.io.IOException

class BandejaRemoteDataSource(
    private val retrofit: RetrofitBuilder
) : IBandejaRemoteDataSource {

    override suspend fun getByAgent(agentId: String, token: String): NetworkResult<List<Bandeja>> {
        return try {
            val response = retrofit.mtgApiService.getByAgent(agentId, token)
            if (response.isSuccessful) {
                val body = response.body()
                if(body != null) {
                    NetworkResult.Success(body.map { it.toModel() })
                } else {
                    NetworkResult.Error("Respuesta vacía del servidor")
                }
            } else {
                NetworkResult.Error("Error de respuesta")
            }
        } catch (e: IOException) {
            NetworkResult.Error("Error en la conexcion: ${e.message}")
        }
    }

    override suspend fun takeRequest(id: String, agentId: String, token: String): NetworkResult<Bandeja> {
        return try {
            println("LLEGA ACA" + id + agentId)

            val response = retrofit.mtgApiService.takeRequest(id, TakeRequestBody(agentId), token)
            println("ESTA ES LA RESPUESTA" + response)
            if(response.isSuccessful) {
                val body = response.body()
                if(body != null) {
                    println(response)
                    NetworkResult.Success(body.toModel())
                }
                else {
                    NetworkResult.Error("Respuesta vacía")
                }
            } else {
                NetworkResult.Error("Error de respuesta")
            }
        } catch (e: IOException) {
            NetworkResult.Error("Error en la conexión: ${e.message}")
        }
    }
}