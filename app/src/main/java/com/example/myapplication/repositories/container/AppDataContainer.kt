package com.example.myapplication.repositories.container

import android.content.Context
import com.example.myapplication.HighScoreDatabase
import com.example.myapplication.repositories.PlayersRepository
import com.example.myapplication.repositories.PlayersRepositoryImpl

class AppDataContainer(private val context: Context) : AppContainer {
    override val playersRepository: PlayersRepository by lazy {
        PlayersRepositoryImpl(HighScoreDatabase.getDatabase(context).playerDao())
    }
    //tutaj dodać implementację wszystkich repozytoriów...
}