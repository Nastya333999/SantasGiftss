package com.app.santasgifts

import android.content.Context
import com.onesignal.OneSignal

class OW(context: Context, uid: String) {
    init {
        OneSignal.initWithContext(context)
        OneSignal.setAppId("7e84b66d-bed5-4097-8e81-20c173116b06")
        OneSignal.setExternalUserId(uid)
    }

    fun send(campaign: String, deep: String) {
        when {
            campaign == "null" && deep == "null" -> {
                OneSignal.sendTag("key2", "organic")
            }
            deep != "null" -> {
                OneSignal.sendTag("key2", deep.replace("myapp://", "").substringBefore("/"))
            }
            campaign != "null" -> {
                OneSignal.sendTag("key2", campaign.substringBefore("_"))
            }
        }
    }
}