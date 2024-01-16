package com.example.myapplication.modules

import com.example.myapplication.repositories.ScoresRepository
import com.example.myapplication.repositories.ScoresRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ScoresModule {
    @Binds //tworzy metodę łączącą z interfejsem
    abstract fun bindScoresRepository(scoresRepositoryImpl: ScoresRepositoryImpl): ScoresRepository
}