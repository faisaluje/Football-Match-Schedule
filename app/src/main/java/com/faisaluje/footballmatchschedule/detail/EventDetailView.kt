package com.faisaluje.footballmatchschedule.detail

import com.faisaluje.footballmatchschedule.model.Event
import com.faisaluje.footballmatchschedule.model.Team

interface EventDetailView{
    fun showLoading()
    fun hideLoading()
//    fun showDetail(eventDetail: Event, homeTeam: Team, awayTeam: Team)
    fun showDetail(eventDetails: List<Event>, homeTeams: List<Team>, awayTeams: List<Team>)
}