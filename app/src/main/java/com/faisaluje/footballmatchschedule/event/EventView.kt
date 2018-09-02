package com.faisaluje.footballmatchschedule.event

import com.faisaluje.footballmatchschedule.model.Event

interface EventView{
    fun showLoading()
    fun hideLoading()
    fun showList(data: List<Event>)
}