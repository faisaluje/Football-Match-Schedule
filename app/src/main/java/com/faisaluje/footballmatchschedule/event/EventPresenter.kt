package com.faisaluje.footballmatchschedule.event

import com.faisaluje.footballmatchschedule.api.ApiRepository
import com.faisaluje.footballmatchschedule.api.TheSportDBApi
import com.faisaluje.footballmatchschedule.model.MatchResponse
import com.faisaluje.footballmatchschedule.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class EventPresenter(private val view: EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val fixture: Int = 1,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getList(id: String?){
        view.showLoading()

        val api = if (fixture == 1) TheSportDBApi.getPrevSchedule(id) else TheSportDBApi.getNextSchedule(id)

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(api), MatchResponse::class.java)
            }

            view.showList(data.await().events)
            view.hideLoading()
        }
    }

}