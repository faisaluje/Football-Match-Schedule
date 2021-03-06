package com.faisaluje.footballmatchschedule.api

import android.net.Uri
import com.faisaluje.footballmatchschedule.BuildConfig

class TheSportDBApi(val id: String?){
    private fun urlBuild(path: String?) = Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath(path)
            .appendQueryParameter("id", id)
            .build().toString()

    fun getPrevSchedule() = urlBuild("eventspastleague.php")
    fun getNextSchedule() = urlBuild("eventsnextleague.php")
    fun getMatchDetail() = urlBuild("lookupevent.php")
    fun getTeamDetail() = urlBuild("lookupteam.php")
}