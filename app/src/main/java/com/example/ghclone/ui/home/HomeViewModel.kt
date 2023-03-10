package com.example.ghclone.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.FavoriteRepository
import com.example.domain.usecases.GetFavoriteRepositoryUseCase
import com.example.domain.utils.Resource
import com.example.ghclone.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getFavoriteRepositoryUseCase: GetFavoriteRepositoryUseCase,
) : ViewModel() {

    private val _favoriteRepositoryState: MutableStateFlow<UIState<FavoriteRepository>> =
        MutableStateFlow(UIState.Empty)
    val favoriteRepositoryState: StateFlow<UIState<FavoriteRepository>> = _favoriteRepositoryState

    init {
        getFavoriteRepositories()
    }

    fun getFavoriteRepositories() {
        viewModelScope.launch(context = coroutineDispatcher) {
            _favoriteRepositoryState.emit(UIState.Loading)
            when (val response = getFavoriteRepositoryUseCase()) {
                is Resource.Success -> {
                    _favoriteRepositoryState.emit(UIState.Success(data = response.data))
                }
                is Resource.Failure -> {
                    _favoriteRepositoryState.emit(UIState.Failure(exception = response.exception))
                }
            }
        }
    }
}
