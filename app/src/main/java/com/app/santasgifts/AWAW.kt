package com.app.santasgifts

import com.appsflyer.AppsFlyerConversionListener

interface AWAW : AppsFlyerConversionListener {
    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
    override fun onAttributionFailure(p0: String?) {}
}
