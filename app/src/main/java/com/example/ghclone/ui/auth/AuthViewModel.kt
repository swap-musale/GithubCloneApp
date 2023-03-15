package com.example.ghclone.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetIsLoggedInUseCase
import com.example.domain.usecases.GetTokenUseCase
import com.example.domain.utils.Resource
import com.example.ghclone.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getTokensUseCase: GetTokenUseCase,
    private val getIsLoggedInUseCase: GetIsLoggedInUseCase,
) : ViewModel() {

    private val _isLoggedIn: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    private val _authState: MutableStateFlow<UIState<String>> = MutableStateFlow(UIState.Empty)
    val authState: StateFlow<UIState<String>> = _authState

    init {
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        viewModelScope.launch(coroutineDispatcher) {
            getIsLoggedInUseCase().collect {
                _isLoggedIn.emit(it)
            }
        }
    }

    fun authenticate(code: String) {
        viewModelScope.launch(context = coroutineDispatcher) {
            _authState.emit(UIState.Loading)
            when (val response = getTokensUseCase(code = code)) {
                is Resource.Success -> {
                    _authState.emit(UIState.Success(data = response.data))
                }
                is Resource.Failure -> {
                    _authState.emit(UIState.Failure(exception = response.exception))
                }
            }
        }
    }
}
