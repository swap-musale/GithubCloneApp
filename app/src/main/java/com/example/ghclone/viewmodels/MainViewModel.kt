package com.example.ghclone.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Resource
import com.example.domain.entities.User
import com.example.domain.usecases.GetIsLoggedInUseCase
import com.example.domain.usecases.GetTokensUseCase
import com.example.domain.usecases.GetUserUseCase
import com.example.ghclone.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getTokensUseCase: GetTokensUseCase,
    private val getIsLoggedInUseCase: GetIsLoggedInUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _isLoggedIn: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    private val _authState: MutableStateFlow<UIState<Unit>> = MutableStateFlow(UIState.Empty)
    val authState: StateFlow<UIState<Unit>> = _authState

    private val _profileState: MutableStateFlow<UIState<User>> = MutableStateFlow(UIState.Empty)
    val profileState: StateFlow<UIState<User>> = _profileState

    init {
        viewModelScope.launch {
            getIsLoggedInUseCase().collect {
                _isLoggedIn.emit(it)
            }
        }
    }

    fun authenticate(code: String) {
        viewModelScope.launch {
            _authState.emit(UIState.Loading)
            when (val response = getTokensUseCase(code = code)) {
                is Resource.Failure -> {
                    _authState.emit(UIState.Failure(exception = response.exception))
                }
                is Resource.Success -> {
                    _authState.emit(UIState.Success(data = Unit))
                }
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            _profileState.emit(UIState.Loading)
            when (val response = getUserUseCase()) {
                is Resource.Failure -> {
                    _profileState.emit(UIState.Failure(exception = response.exception))
                }
                is Resource.Success -> {
                    _profileState.emit(UIState.Success(data = response.data))
                }
            }
        }
    }
}
