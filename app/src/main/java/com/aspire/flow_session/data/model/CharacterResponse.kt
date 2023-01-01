package com.aspire.flow_session.data.model

import com.aspire.flow_session.data.local.CharacterEntity
import com.aspire.flow_session.domain.Character
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    @Json(name = "results")
    val CharactersList: List<CharacterEntity>
)

fun CharacterResponse.toCharacters() =
    CharactersList.map { Character(it.name, it.image, false) }
