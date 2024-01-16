package com.example.myapplication.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.Player
import com.example.myapplication.repositories.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
//class ProfileViewModel(private val playersRepository: PlayersRepository) : ViewModel()
class ProfileViewModel @Inject constructor(private val playersRepository: PlayersRepository) : ViewModel()
{
    var playerId = mutableStateOf(0L)
    val name = mutableStateOf("")
    val email = mutableStateOf("")
    suspend fun savePlayer() {
        var player = playersRepository.getPlayersByEmail(email.value)
        if (player.isEmpty()) {
            var newPlayer=Player(name=name.value, email = email.value)
            playersRepository.insertPlayer(newPlayer)
            newPlayer = playersRepository.getPlayersByEmail(email.value).first()
            playersRepository.setCurrentPlayerId(newPlayer.playerId)
        }else{
           var updatePlayer=Player(playerId=player.first().playerId, name=name.value, email = player.first().email)
            playersRepository.updatePlayer(updatePlayer)
            playersRepository.setCurrentPlayerId(updatePlayer.playerId)
        }
    }
}