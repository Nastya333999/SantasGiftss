package com.app.santasgifts

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.greendao.database.Database


@HiltAndroidApp()
class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }

//    fun getDaoSession(): DaoSession? {
//        return daoSession
//    }

}