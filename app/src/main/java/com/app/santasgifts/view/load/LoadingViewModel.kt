package com.app.santasgifts.view.load

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.santasgifts.*
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoadingViewModel(private val app: Application) : BaseViewModel(app) {
    val _data = MutableLiveData<String>()


    fun init(activity: LoadingActivity) {
        viewModelScope.launch(Dispatchers.IO) {

            val daoSession: DaoSession = (app as App).daoSession
            val itemUrlDao = daoSession.itemUrlDao
            val items = itemUrlDao.loadAll()

            if (items.isNotEmpty()) {
                _data.postValue(items.first().url)
            } else {

                val apps = getAppsflyer(activity)
                Log.e("init", "getAppsflyer finished")

                val deep = deepFlow(activity)
                Log.e("init", "deepFlow finished")

                val adId = AdvertisingIdClient.getAdvertisingIdInfo(activity).id.toString()
                Log.e("init", "AdvertisingIdClient finished")

                val uId = AppsFlyerLib.getInstance().getAppsFlyerUID(activity)!!
                Log.e("init", "AppsFlyerLib finished")


                val url = FileDataCreater.createUrl(
                    res = app.resources,
                    baseFileData = "whitewater.agency/" + "santasgifts.php",
                    gadid = adId,
                    apps = if (deep == "null") apps else null,
                    deep = deep,
                    uid = if (deep == "null") uId else null
                )
                _data.postValue(url)
                Log.e("init", "url is - $url")
            }
        }
    }


    private suspend fun getAppsflyer(activity: LoadingActivity): MutableMap<String, Any>? =
        suspendCoroutine { coroutine ->
            Log.e("Initialization", "start appsFlow")

            val callback = object : AppsWrapper {
                override fun onConversionDataSuccess(convData: MutableMap<String, Any>?) {

                    Log.e("Initialization", "onConversionDataSuccess $convData")
                    coroutine.resume(convData)
                }

                override fun onConversionDataFail(p0: String?) {
                    Log.e("Initialization", "onConversionDataFail $p0")
                    coroutine.resume(null)
                }
            }
            AppsFlyerLib.getInstance().init("9ZZLnrwK3ZcFbMTYw7wsmh", callback, activity)
            Log.e("Initialization", "init appsFlow")

            AppsFlyerLib.getInstance().start(activity)

            Log.e("Initialization", "end appsFlow")

        }

    private suspend fun deepFlow(activity: LoadingActivity): String =
        suspendCoroutine { coroutine ->
            Log.e("Initialization", "deepFlow start")

            val callback = AppLinkData.CompletionHandler {
                Log.e("Initialization", "deepFlow callback = $it")
                coroutine.resume(it?.targetUri.toString())
            }
            AppLinkData.fetchDeferredAppLinkData(activity, callback)
            Log.e("Initialization", "deepFlow end")
        }

}

