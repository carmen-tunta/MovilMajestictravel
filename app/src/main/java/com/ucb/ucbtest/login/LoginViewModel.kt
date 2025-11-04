package com.ucb.ucbtest.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.NetworkResult
import com.ucb.domain.User
import com.ucb.framework.datastore.LoginDataSource
import com.ucb.usecases.user.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val userLogin: Login,
    val loginDataSource: LoginDataSource,
): ViewModel() {

    sealed class LoginState {
        object Init: LoginState()
        object Loading: LoginState()
        data class Successful(val user: User): LoginState()
        class Error(val message: String): LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Init)
    var loginState : StateFlow<LoginState> = _loginState

    fun saveUserLocally(user: User) {
        viewModelScope.launch {
            loginDataSource.saveUser(user)
        }
    }
    fun DoLogin(username: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            val result: NetworkResult<User> = userLogin.invoke(username, password)

            when (result) {
                is NetworkResult.Success -> {
                    loginDataSource.saveUser(result.data)
                    _loginState.value = LoginState.Successful(result.data)
                }
                is NetworkResult.Error -> {
                    _loginState.value = LoginState.Error("Error al hacer el login")
                }
            }
        }
    }

}