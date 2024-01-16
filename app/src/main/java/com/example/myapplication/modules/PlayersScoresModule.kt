package com.example.myapplication.modules

import com.example.myapplication.repositories.PlayersScoresRepository
import com.example.myapplication.repositories.PlayersScoresRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PlayersScoresModule {
    @Binds //tworzy metodę łączącą z interfejsem
    abstract fun bindPlayersScoresRepository(playersscoresRepositoryImpl: PlayersScoresRepositoryImpl): PlayersScoresRepository
}