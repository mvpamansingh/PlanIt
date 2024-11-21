package com.example.planit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class AppApplication :Application()
{
    override fun onCreate() {
        super.onCreate()

        startKoin {

            modules(

            )
            androidContext(this@AppApplication)
        }
    }

}