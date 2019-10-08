package com.sq.dir

import android.app.Application

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object{
        lateinit var INSTANCE : App

        fun get(): App {
            return INSTANCE
        }
    }

}