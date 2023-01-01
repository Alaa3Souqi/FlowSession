package com.aspire.flow_session.network

import com.aspire.flow_session.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun fetchCharacters(@Query("page") page: String): CharacterResponse

}