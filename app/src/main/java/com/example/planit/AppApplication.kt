package com.example.planit

import android.app.Application
import com.example.planit.data.di.dataModule
import com.example.planit.presentation.common.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class AppApplication :Application()
{
    override fun onCreate() {
        super.onCreate()

        startKoin {

            modules(

                dataModule, presentationModule
            )
            androidContext(this@AppApplication)
        }
    }

}