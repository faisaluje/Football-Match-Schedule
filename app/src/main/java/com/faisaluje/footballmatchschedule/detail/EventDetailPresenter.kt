package com.faisaluje.footballmatchschedule.detail

import com.faisaluje.footballmatchschedule.api.ApiRepository
import com.faisaluje.footballmatchschedule.api.TheSportDBApi
import com.faisaluje.footballmatchschedule.model.ApiResponse
import com.faisaluje.footballmatchschedule.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class EventDetailPresenter(private val view: EventDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getEventDetail(eventId: String?, homeTeamId: String?, awayTeamId: String?){
        view.showLoading()

        async(context.main) {
            val matchDetail = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getMatchDetail(eventId)), ApiResponse::class.java)
            }
            val homeTeam = bg {
                gson.fromJson(ApiRepository().doRequest(TheSportDBApi.getTeamDetail(homeTeamId)), ApiResponse::class.java)
            }
            val awayTeam = bg {
                gson.fromJson(ApiRepository().doRequest(TheSportDBApi.getTeamDetail(awayTeamId)), ApiResponse::class.java)
            }

            view.showDetail(matchDetail.await().events[0], homeTeam.await().teams[0], awayTeam.await().teams[0])
            view.hideLoading()
        }
    }

}