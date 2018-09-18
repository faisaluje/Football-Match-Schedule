package com.faisaluje.footballmatchschedule.event

import com.faisaluje.footballmatchschedule.api.ApiRepository
import com.faisaluje.footballmatchschedule.model.ApiResponse
import com.faisaluje.footballmatchschedule.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class EventPresenter(private val view: EventView,
                     private val api: String,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getList(){
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(ApiRepository().doRequest(api), ApiResponse::class.java)
            }

            view.showList(data.await().events)
            view.hideLoading()
        }
    }

}