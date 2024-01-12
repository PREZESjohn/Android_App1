package com.example.myapplication.providers

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.MasterAndApplication
import com.example.myapplication.viewmodels.ProfileViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProfileViewModel(masterAndApplication().container.playersRepository)
        }
        //tutaj dodać inicializator dla każdego ViewModelu...
    }
}
fun CreationExtras.masterAndApplication(): MasterAndApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
            MasterAndApplication)