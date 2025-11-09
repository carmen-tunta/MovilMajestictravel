package com.mtg.peruandestop.bandeja

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtg.data.NetworkResult
import com.mtg.domain.Bandeja
import com.mtg.framework.datastore.LoginDataSource
import com.mtg.usecases.bandeja.AtenderRequest
import com.mtg.usecases.bandeja.CotizandoRequest
import com.mtg.usecases.bandeja.GetBandejaByAgent
import com.mtg.usecases.bandeja.ReleaseRequest
import com.mtg.usecases.bandeja.RequestSinRespuesta
import com.mtg.usecases.bandeja.TakeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BandejaViewModel @Inject constructor(
    val getByAgent: GetBandejaByAgent,
    val takeRequest: TakeRequest,
    val releaseRequest: ReleaseRequest,
    val cotizandoRequest: CotizandoRequest,
    val requestSinRespuesta: RequestSinRespuesta,
    val atenderRequest: AtenderRequest,
    private val loginDataSource: LoginDataSource,
): ViewModel() {

    val currentUser = loginDataSource.userFlow

    sealed class BandejaState {
        object Init: BandejaState()
        object Loading: BandejaState()
        data class Successful(val bandejas: List<Bandeja>): BandejaState()
        class Error(val message: String): BandejaState()
    }

    private val _bandejaState = MutableStateFlow<BandejaState>(BandejaState.Init)
    var bandejaState : StateFlow<BandejaState> = _bandejaState

    fun DoGetByAgent(agentId: String, token: String) {
        _bandejaState.value = BandejaState.Loading
        viewModelScope.launch {
            val result: NetworkResult<List<Bandeja>> = getByAgent.invoke(agentId, "Bearer $token")

            when(result) {
                is NetworkResult.Success -> {
                    _bandejaState.value = BandejaState.Successful(result.data)
                }
                is NetworkResult.Error -> {
                    _bandejaState.value = BandejaState.Error("No se encontraron datos")
                }
            }
        }
    }

    fun DoTakeRequest(id: String, idAgent: String, token: String) {
        _bandejaState.value = BandejaState.Loading
        viewModelScope.launch {
            val result: NetworkResult<Bandeja> = takeRequest.invoke(id, idAgent, "Bearer $token")

            when(result) {
                is NetworkResult.Success -> {
                    DoGetByAgent(idAgent, token)
                }
                is NetworkResult.Error -> {
                    _bandejaState.value = BandejaState.Error("Error al tomar la solicitud, recarga la página")
                }
            }
        }
    }

    fun DoReleaseRequest(id: String, idAgent: String, token: String) {
        _bandejaState.value = BandejaState.Loading
        viewModelScope.launch {
            val result: NetworkResult<Bandeja> = releaseRequest.invoke(id, idAgent, "Bearer $token")

            when(result) {
                is NetworkResult.Success -> {
                    DoGetByAgent(idAgent, token)
                }
                is NetworkResult.Error -> {
                    _bandejaState.value = BandejaState.Error("Error al liberar la solicitud, recarga la página")
                }
            }
        }
    }

    fun DoCotizandoRequest(id: String, idAgent: String, token: String) {
        _bandejaState.value = BandejaState.Loading
        viewModelScope.launch {
            val result: NetworkResult<Bandeja> = cotizandoRequest.invoke(id, idAgent, "Bearer $token")

            when(result) {
                is NetworkResult.Success -> {
                    DoGetByAgent(idAgent, token)
                }
                is NetworkResult.Error -> {
                    _bandejaState.value = BandejaState.Error("Error al poner como cotizando la solicitud, recarga la página")
                }
            }
        }
    }

    fun DoRequestSinRespuesta(id: String, idAgent: String, token: String) {
        _bandejaState.value = BandejaState.Loading
        viewModelScope.launch {
            val result: NetworkResult<Bandeja> = requestSinRespuesta.invoke(id, idAgent, "Bearer $token")

            when(result) {
                is NetworkResult.Success -> {
                    DoGetByAgent(idAgent, token)
                }
                is NetworkResult.Error -> {
                    _bandejaState.value = BandejaState.Error("Error al poner la solicitud como sin respuesta, recarga la página")
                }
            }
        }
    }

    fun DoAtenderRequest(id: String, idAgent: String, token: String) {
        _bandejaState.value = BandejaState.Loading
        viewModelScope.launch {
            val result: NetworkResult<Bandeja> = atenderRequest.invoke(id, "Bearer $token")

            when(result) {
                is NetworkResult.Success -> {
                    DoGetByAgent(idAgent, token)
                }
                is NetworkResult.Error -> {
                    _bandejaState.value = BandejaState.Error("Error al poner la solicitud como sin respuesta, recarga la página")
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            loginDataSource.logout()
        }
    }
}