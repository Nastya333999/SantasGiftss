package com.app.santasgifts.data

import android.app.Application
import com.app.santasgifts.DaoMaster
import com.app.santasgifts.DaoSession
import com.app.santasgifts.ItemUrl
import com.app.santasgifts.view.Wv.WvActivity
import org.greenrobot.greendao.database.Database
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(app: Application): Repository {

    private val daoSession: DaoSession
    private val db: Database

    init {
        val helper = DaoMaster.DevOpenHelper(app, "MyDataBase", null)
        db = helper.writableDb
        daoSession = DaoMaster(db).newSession()
    }

    override fun getUrl(): String? {
        val itemUrlDao = daoSession.itemUrlDao
        val items = itemUrlDao.loadAll()
        return if(items.isNotEmpty())
            items.first().url
        else
            null
    }

    override fun saveUrl(url: String) {
        val itemUrlDao = daoSession.itemUrlDao
        val items = itemUrlDao.loadAll()

        if (url.isNotEmpty() && !url.contains(WvActivity.BASE_URL) && items.isEmpty()) {
            val itemUrl = ItemUrl()
            itemUrl.url = url
            itemUrlDao.save(itemUrl)
        }
    }
}