package com.aspire.flow_session.di

import android.app.Application
import androidx.room.Room
import com.aspire.flow_session.data.local.CharacterDao
import com.aspire.flow_session.data.local.CharacterDatabase
import com.aspire.flow_session.data.repository.RickMortyRepository
import com.aspire.flow_session.data.repository.RickMortyRepositoryImpl
import com.aspire.flow_session.network.ApiService
import com.aspire.flow_session.network.Constants.RICK_MORTY_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMoshi(): MoshiConverterFactory =
        MoshiConverterFactory.create(
            Moshi.Builder().add(
                KotlinJsonAdapterFactory()
            ).build()
        )

    @Singleton
    @Provides
    fun provideRetrofit(moshi: MoshiConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl(RICK_MORTY_URL)
            .addConverterFactory(moshi)
            .build()

    @Singleton
    @Provides
    fun provideApiServices(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideCharactersDatabase(app: Application): CharacterDao {
        return Room.databaseBuilder(app, CharacterDatabase::class.java, "word_db").build().dao
    }

    @Provides
    @Singleton
    fun provideRickMortyRepo(repository: RickMortyRepositoryImpl): RickMortyRepository =
        repository
}
