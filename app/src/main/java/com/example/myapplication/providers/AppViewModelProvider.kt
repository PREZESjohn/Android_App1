package com.example.myapplication.providers

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.MasterAndApplication
import com.example.myapplication.viewmodels.GameViewModel
import com.example.myapplication.viewmodels.ProfileViewModel
import com.example.myapplication.viewmodels.ResultViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProfileViewModel(masterAndApplication().container.playersRepository)
        }
        initializer {
            GameViewModel(masterAndApplication().container.playersRepository,
                masterAndApplication().container.scoresRepository)
        }
        initializer {
            ResultViewModel(masterAndApplication().container.playersScoresRepository)
        }
    }
}
fun CreationExtras.masterAndApplication(): MasterAndApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
            MasterAndApplication)