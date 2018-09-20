package com.faisaluje.footballmatchschedule.api

import android.net.Uri
import com.faisaluje.footballmatchschedule.BuildConfig

object TheSportDBApi{
//    private fun urlBuild(path: String?) = Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath(path)
//            .appendQueryParameter("id", id)
//            .build().toString()

    private fun urlBuild(path: String, id: String?) = BuildConfig.BASE_URL+"api/v1/json/"+BuildConfig.TSDB_API_KEY+"/"+path+"?id="+id

    fun getPrevSchedule(id: String?) = urlBuild("eventspastleague.php", id)
    fun getNextSchedule(id: String?) = urlBuild("eventsnextleague.php", id)
    fun getMatchDetail(id: String?) = urlBuild("lookupevent.php", id)
    fun getTeamDetail(id: String?) = urlBuild("lookupteam.php", id)
}