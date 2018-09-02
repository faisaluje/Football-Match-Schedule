package com.faisaluje.footballmatchschedule.detail

import com.faisaluje.footballmatchschedule.model.Event

interface EventDetailView{
    fun showLoading()
    fun hideLoading()
    fun showDetail(eventDetail: Event, homeTeam: Event, awayTeam: Event)
}