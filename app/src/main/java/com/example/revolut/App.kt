package com.example.revolut

import android.app.Application
import com.example.revolut.repository.Repository
import com.example.revolut.repository.RepositoryImpl
import com.example.revolut.repository.ResponseParserImpl

class App : Application() {

    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        val parser = ResponseParserImpl()
        repository = RepositoryImpl(parser)
        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}