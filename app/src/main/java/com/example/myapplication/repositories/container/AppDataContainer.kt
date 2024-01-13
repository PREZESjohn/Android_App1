package com.example.myapplication.repositories.container

import android.content.Context
import com.example.myapplication.HighScoreDatabase
import com.example.myapplication.repositories.PlayersRepository
import com.example.myapplication.repositories.PlayersRepositoryImpl
import com.example.myapplication.repositories.PlayersScoresRepository
import com.example.myapplication.repositories.PlayersScoresRepositoryImpl
import com.example.myapplication.repositories.ScoresRepository
import com.example.myapplication.repositories.ScoresRepositoryImpl

class AppDataContainer(private val context: Context) : AppContainer {
    override val playersRepository: PlayersRepository by lazy {
        PlayersRepositoryImpl(HighScoreDatabase.getDatabase(context).playerDao())
    }
    override val scoresRepository: ScoresRepository by lazy {
        ScoresRepositoryImpl(HighScoreDatabase.getDatabase(context).scoreDao())
    }
    override val playersScoresRepository: PlayersScoresRepository by lazy {
        PlayersScoresRepositoryImpl(HighScoreDatabase.getDatabase(context).playerScoreDao())
    }
}