package com.aspire.flow_session.data.repository

import com.aspire.flow_session.data.local.CharacterDao
import com.aspire.flow_session.data.local.CharacterEntity
import com.aspire.flow_session.data.model.toCharacters
import com.aspire.flow_session.domain.Character
import com.aspire.flow_session.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RickMortyRepository {
    fun getCharacter(): Flow<List<Character>>
    fun getFavorite(): Flow<List<CharacterEntity>>

    suspend fun add(characters: Character)

    suspend fun remove(characters: Character)
}

class RickMortyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: CharacterDao
) : RickMortyRepository {

    override fun getCharacter() =
        flow { emit(apiService.fetchCharacters("1").toCharacters()) }.flowOn(Dispatchers.IO)

    override fun getFavorite() = dao.getCharacters()

    override suspend fun add(characters: Character) {
        dao.saveCharacter(CharacterEntity(characters.name, characters.image))
    }

    override suspend fun remove(characters: Character) {
        dao.deleteCharacter(CharacterEntity(characters.name, characters.image))
    }
}