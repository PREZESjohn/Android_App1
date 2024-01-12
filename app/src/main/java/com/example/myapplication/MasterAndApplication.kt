package com.example.myapplication

import android.app.Application
import com.example.myapplication.repositories.container.AppContainer
import com.example.myapplication.repositories.container.AppDataContainer

class MasterAndApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}