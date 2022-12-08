package com.app.santasgifts

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.greendao.database.Database


@HiltAndroidApp()
class App : Application() {

    lateinit var daoSession: DaoSession
    private lateinit var db: Database

    override fun onCreate() {
        super.onCreate()


        val helper = DaoMaster.DevOpenHelper(this, "MyDataBase", null)
        db = helper.writableDb
        daoSession = DaoMaster(db).newSession()


    }

//    fun getDaoSession(): DaoSession? {
//        return daoSession
//    }

}