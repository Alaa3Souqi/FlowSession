package com.aspire.flow_session.ui.rickMorty

import com.aspire.flow_session.domain.Character

sealed class RickMortyEvents {
    data class FavoriteClick(val character: Character) : RickMortyEvents()
    object TryAgainCharacter : RickMortyEvents()
    object TryAgainFavorite : RickMortyEvents()
}