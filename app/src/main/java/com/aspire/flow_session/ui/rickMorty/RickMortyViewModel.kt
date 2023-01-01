package com.aspire.flow_session.ui.rickMorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspire.flow_session.data.repository.RickMortyRepository
import com.aspire.flow_session.domain.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickMortyViewModel @Inject constructor(
    private val repository: RickMortyRepository
) : ViewModel() {

    private val _characters = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val characters = _characters.asStateFlow()

    private val _favoriteCharacters = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val favoriteCharacters = _favoriteCharacters.asStateFlow()

    private fun getCharacters() {
        repository.getCharacter()
            .onEach { characters ->
                repository.getFavorite()
                    .map { it.map { item -> Character(item.name, item.image, false) } }
                    .collect { favorite ->
                        _characters.value = ScreenState.Success(characters.map { item ->
                            item.copy(isSaved = favorite.contains(item))
                        })
                    }
            }
            .onStart { _characters.value = ScreenState.Loading }
            .catch { _characters.value = ScreenState.Error(it) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun getFavorite() {
        repository.getFavorite()
            .map { it.map { item -> Character(item.name, item.image, true) } }
            .map { if (it.isEmpty()) ScreenState.Empty else ScreenState.Success(it) }
            .onStart { emit(ScreenState.Loading) }
            .catch { emit(ScreenState.Error(it)) }
            .onEach { _favoriteCharacters.emit(it) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun saveCharacter(characters: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(characters)
        }
    }

    private fun removeCharacter(characters: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.remove(characters)
        }
    }

    fun onEvent(events: RickMortyEvents) {
        when (events) {
            is RickMortyEvents.FavoriteClick -> {
                if (events.character.isSaved) {
                    removeCharacter(events.character)
                } else {
                    saveCharacter(events.character)
                }
            }
            RickMortyEvents.TryAgainCharacter -> {
                getCharacters()
            }
            RickMortyEvents.TryAgainFavorite -> {
                getFavorite()
            }
        }
    }

    init {
        getCharacters()
        getFavorite()
    }

    sealed class ScreenState {
        object Empty : ScreenState()
        class Success(val items: List<Character>) : ScreenState()
        class Error(val throwable: Throwable) : ScreenState()
        object Loading : ScreenState()
    }
}