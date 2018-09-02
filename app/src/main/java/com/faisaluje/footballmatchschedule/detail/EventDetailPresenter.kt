package com.faisaluje.footballmatchschedule.detail

import com.faisaluje.footballmatchschedule.api.ApiRepository
import com.faisaluje.footballmatchschedule.model.ApiResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventDetailPresenter(private val view: EventDetailView,
                           private val apiMatchDetail: String,
                           private val apiHomeTeam: String,
                           private val apiAwayTeam: String,
                           private val gson: Gson){

    fun getEventDetail(){
        view.showLoading()

        doAsync {
            val matchDetail = gson.fromJson(ApiRepository().doRequest(apiMatchDetail), ApiResponse::class.java)
            val homeTeam = gson.fromJson(ApiRepository().doRequest(apiHomeTeam), ApiResponse::class.java)
            val awayTeam = gson.fromJson(ApiRepository().doRequest(apiAwayTeam), ApiResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showDetail(matchDetail.events[0], homeTeam.teams[0], awayTeam.teams[0])
            }
        }
    }

}