package com.app.santasgifts.view.load

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.santasgifts.*
import com.app.santasgifts.data.Repository
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val repository: Repository,
    private val app: Application,
) : BaseViewModel(app) {
    val _data = MutableLiveData<String>()

    fun innn(activity: LoadingActivity) {
        viewModelScope.launch(Dispatchers.IO) {

            val url = repository.getUrl()

            if (url.isNullOrEmpty()) {
                _data.postValue(url)
            } else {
                val apps = gAF(activity)
                val deep = dF(activity)
                val adId = AdvertisingIdClient.getAdvertisingIdInfo(activity).id.toString()
                val uId = AppsFlyerLib.getInstance().getAppsFlyerUID(activity)!!
                val url = FDC.createUrl(
                    res = app.resources,
                    baseFileData = "whitewater.agency/" + "santasgifts.php",
                    gadid = adId,
                    apps = if (deep == "null") apps else null,
                    deep = deep,
                    uid = if (deep == "null") uId else null
                )
                _data.postValue(url)
            }
        }
    }


    private suspend fun gAF(activity: LoadingActivity): MutableMap<String, Any>? =
        suspendCoroutine { coroutine ->
            val callback = object : AWAW {
                override fun onConversionDataSuccess(convData: MutableMap<String, Any>?) {
                    coroutine.resume(convData)
                }

                override fun onConversionDataFail(p0: String?) {
                    coroutine.resume(null)
                }
            }
            AppsFlyerLib.getInstance().init("9ZZLnrwK3ZcFbMTYw7wsmh", callback, activity)
            AppsFlyerLib.getInstance().start(activity)
        }

    private suspend fun dF(activity: LoadingActivity): String =
        suspendCoroutine { coroutine ->
            val callback = AppLinkData.CompletionHandler {
                coroutine.resume(it?.targetUri.toString())
            }
            AppLinkData.fetchDeferredAppLinkData(activity, callback)
        }

}

