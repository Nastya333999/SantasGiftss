package com.app.santasgifts

import android.content.res.Resources
import net.moznion.uribuildertiny.URIBuilderTiny
import java.util.*

class FDC {
    companion object {
        fun createUrl(
            res: Resources,
            baseFileData: String,
            gadid: String,
            apps: MutableMap<String, Any>?,
            deep: String,
            uid: String?,
        ): String {

            val queryParameters: MutableMap<String, String> = HashMap()
            queryParameters[res.getString(R.string.smvv)] =
                res.getString(R.string.hbdncnv)

            queryParameters[res.getString(R.string.bcbc)] = TimeZone.getDefault().id
            queryParameters[res.getString(R.string.qcsvbct)] = gadid
            queryParameters[res.getString(R.string.vcnsm)] = deep

            queryParameters[res.getString(R.string.vsfsw)] =
                if (deep != "null") "deeplink" else apps?.get("media_source").toString()

            queryParameters[res.getString(R.string.gvcg)] = uid.toString()

            queryParameters[res.getString(R.string.asdcfg)] = apps?.get("adset_id").toString()
            queryParameters[res.getString(R.string.wsdftgb)] =
                apps?.get("campaign_id").toString()
            queryParameters[res.getString(R.string._mnbh)] =
                apps?.get("campaign").toString()
            queryParameters[res.getString(R.string.nvkd)] = apps?.get("adset").toString()
            queryParameters[res.getString(R.string.vnd)] = apps?.get("adgroup").toString()
            queryParameters[res.getString(R.string.dqfs)] =
                apps?.get("orig_cost").toString()
            queryParameters[res.getString(R.string.sqgx__h)] =
                apps?.get("af_siteid").toString()


            val url = URIBuilderTiny()
                .setScheme("https")
                .setRawHost(baseFileData)
                .setQueryParameters(queryParameters)
                .build()
                .toString()

            return url
        }
    }
}