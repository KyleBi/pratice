package com.github.kotlinapp

import android.app.Application
import com.github.kotlinapp.extensions.DelegatesExt

class App : Application() {


    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}