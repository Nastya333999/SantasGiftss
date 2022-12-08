package com.app.santasgifts

import android.content.res.Resources
import net.moznion.uribuildertiny.URIBuilderTiny
import java.util.*

class FileDataCreater {
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
            queryParameters[res.getString(R.string.secure_get_parametr)] =
                res.getString(R.string.secure_key)

            queryParameters[res.getString(R.string.dev_tmz_key)] = TimeZone.getDefault().id
            queryParameters[res.getString(R.string.gadid_key)] = gadid
            queryParameters[res.getString(R.string.deeplink_key)] = deep

            queryParameters[res.getString(R.string.source_key)] =
                if (deep != "null") "deeplink" else apps?.get("media_source").toString()

            queryParameters[res.getString(R.string.af_id_key)] = uid.toString()

            queryParameters[res.getString(R.string.adset_id_key)] = apps?.get("adset_id").toString()
            queryParameters[res.getString(R.string.campaign_id_key)] =
                apps?.get("campaign_id").toString()
            queryParameters[res.getString(R.string.app_campaign_key)] =
                apps?.get("campaign").toString()
            queryParameters[res.getString(R.string.adset_key)] = apps?.get("adset").toString()
            queryParameters[res.getString(R.string.adgroup_key)] = apps?.get("adgroup").toString()
            queryParameters[res.getString(R.string.orig_cost_key)] =
                apps?.get("orig_cost").toString()
            queryParameters[res.getString(R.string.af_siteid_key)] =
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