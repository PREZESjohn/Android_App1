package com.example.myapplication.modules

import com.example.myapplication.repositories.PlayersRepository
import com.example.myapplication.repositories.PlayersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PlayersModule {
    @Binds //tworzy metodę łączącą z interfejsem
    abstract fun bindPlayersRepository(playersRepositoryImpl: PlayersRepositoryImpl): PlayersRepository
}