package com.example.myapplication.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.Player
import com.example.myapplication.models.Score
import com.example.myapplication.repositories.PlayersRepository
import com.example.myapplication.repositories.ScoresRepository

class GameViewModel(val playersRepository: PlayersRepository, val scoresRepository: ScoresRepository): ViewModel() {
    var playerId =playersRepository.getCurrentPlayerId()
    var score = mutableStateOf(0L)
    suspend fun savePlayerScore(){
        var score=Score(playerId = playerId.value!!, value = score.value )
        scoresRepository.insertScore(score)
    }


}