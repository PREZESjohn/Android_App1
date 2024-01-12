package com.example.myapplication.repositories.container

import com.example.myapplication.repositories.PlayersRepository

interface AppContainer {
    val playersRepository: PlayersRepository
    //tutaj dodać deklarację wszystkich repozytoriów...
}