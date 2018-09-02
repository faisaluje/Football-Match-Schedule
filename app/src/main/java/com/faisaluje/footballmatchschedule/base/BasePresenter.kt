package com.faisaluje.footballmatchschedule.base

import com.faisaluje.footballmatchschedule.api.ApiRepository
import com.faisaluje.footballmatchschedule.model.ApiResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

open class BasePresenter(private val view: BaseView,
                         private val api: String,
                         private val gson: Gson){

    fun getList(){
        view.showLoading()

        doAsync {
            val data = gson.fromJson(ApiRepository().doRequest(api), ApiResponse::class.java)

            uiThread {
                view.hideLoading()
                if(data.events.isEmpty()) view.showList(data.teams) else view.showList(data.events)
            }
        }
    }

}