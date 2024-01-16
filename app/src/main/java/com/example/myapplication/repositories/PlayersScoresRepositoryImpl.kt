package com.example.myapplication.repositories

import com.example.myapplication.dao.PlayerScoreDao
import com.example.myapplication.models.PlayerWithScore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

//class PlayersScoresRepositoryImpl(private val playerScoreDao: PlayerScoreDao): PlayersScoresRepository
@Singleton
class PlayersScoresRepositoryImpl @Inject constructor(private val playerScoreDao: PlayerScoreDao): PlayersScoresRepository {

    override fun loadPlayersWithScores(): Flow<List<PlayerWithScore>> = playerScoreDao.loadPlayersWithScores()


}