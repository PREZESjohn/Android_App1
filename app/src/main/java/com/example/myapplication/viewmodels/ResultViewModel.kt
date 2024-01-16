package com.example.myapplication.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.PlayerWithScore
import com.example.myapplication.models.Score
import com.example.myapplication.repositories.PlayersRepository
import com.example.myapplication.repositories.PlayersScoresRepository
import com.example.myapplication.repositories.ScoresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
//class ResultViewModel(val playersScoresRepository: PlayersScoresRepository): ViewModel() {
class ResultViewModel @Inject constructor(private val playersScoresRepository: PlayersScoresRepository): ViewModel() {
    fun loadPlayersScores(): Flow<List<PlayerWithScore>> {
        return playersScoresRepository.loadPlayersWithScores()
    }
}
