package com.example.myapplication.repositories.container

import com.example.myapplication.repositories.PlayersRepository
import com.example.myapplication.repositories.PlayersScoresRepository
import com.example.myapplication.repositories.ScoresRepository

interface AppContainer {
    val playersRepository: PlayersRepository
    val scoresRepository: ScoresRepository
    val playersScoresRepository: PlayersScoresRepository
}