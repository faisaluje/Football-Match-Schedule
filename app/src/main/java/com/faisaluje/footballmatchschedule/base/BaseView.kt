package com.faisaluje.footballmatchschedule.base

import com.faisaluje.footballmatchschedule.model.Event

interface BaseView{
    fun showLoading()
    fun hideLoading()
    fun showList(data: List<Event>)
}