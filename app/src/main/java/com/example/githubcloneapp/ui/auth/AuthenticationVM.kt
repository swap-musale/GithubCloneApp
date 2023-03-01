package com.example.githubcloneapp.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.localStorage.DataStoreManager
import com.example.domain.model.AccessTokenRequest
import com.example.domain.model.GithubUseCases
import com.example.githubcloneapp.util.Constant
import com.example.githubcloneapp.util.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthenticationVM(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val githubUseCases: GithubUseCases,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Any>>(UiState.EmptyList)
    val uiState: StateFlow<UiState<Any>> = _uiState

    fun getUserAccessToken(authorizationCode: String) {
        viewModelScope.launch(ioDispatcher) {
            _uiState.value = UiState.Loading
            val response = githubUseCases.getUserAccessToken(
                request = AccessTokenRequest(
                    client_id = Constant.APP_CLIENT_ID,
                    client_secret = Constant.APP_CLIENT_SECRET,
                    code = authorizationCode
                )
            )
            response?.let {
                _uiState.value = UiState.Success(response)
            }
        }
    }

    fun getUserInfo() {
        try {
            viewModelScope.launch(ioDispatcher) {
                val response = githubUseCases.getUserInfoUseCase()
                Log.d("TAG", "getUserInfo: $response")
                _uiState.value = UiState.Success(response)
            }
        } catch (e: Exception) {
            Log.d("TAG", "Exception: ${e.printStackTrace()}")
        }
    }

    fun saveUserAccessToken(accessToken: String) {
        viewModelScope.launch(ioDispatcher) {
            dataStoreManager.saveUserAccessToken(accessToken = accessToken)
            Log.d("TAG", "savingUserAccessToken: $accessToken")
        }
    }
}