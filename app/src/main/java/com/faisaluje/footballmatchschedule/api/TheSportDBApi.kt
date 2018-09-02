package com.faisaluje.footballmatchschedule.api

import android.net.Uri
import com.faisaluje.footballmatchschedule.BuildConfig
import java.net.URL

class TheSportDBApi(val id: String?){
    private fun getJson(path: String?): String {
        val url = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath(path)
                .appendQueryParameter("id", id)
                .build().toString()

        return URL(url).readText()
    }

    fun getPrevSchedule() = getJson("eventspastleague.php")
    fun getNextSchedule() = getJson("eventsnextleague.php")
    fun getMatchDetail() = getJson("lookupevent.php")
    fun getTeamDetail() = getJson("lookupteam.php")
}