package com.ucb.ucbtest.bandeja

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucb.data.NetworkResult
import com.ucb.domain.Bandeja
import com.ucb.usecases.bandeja.GetBandejaByAgent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BandejaViewModel @Inject constructor(
    val getByAgent: GetBandejaByAgent
): ViewModel() {

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
}