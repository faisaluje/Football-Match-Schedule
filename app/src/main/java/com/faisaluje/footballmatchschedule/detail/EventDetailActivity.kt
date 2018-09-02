package com.faisaluje.footballmatchschedule.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.faisaluje.footballmatchschedule.R
import com.faisaluje.footballmatchschedule.R.color.colorAccent
import com.faisaluje.footballmatchschedule.api.TheSportDBApi
import com.faisaluje.footballmatchschedule.model.Event
import com.faisaluje.footballmatchschedule.util.changeFormatDate
import com.faisaluje.footballmatchschedule.util.strToDate
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.match_detail.*
import org.jetbrains.anko.support.v4.onRefresh

class EventDetailActivity: AppCompatActivity(), EventDetailView{
    private lateinit var event: Event
    private lateinit var presenter: EventDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.match_detail)

        event = intent.getParcelableExtra("EVENT")
        val date = strToDate(event.eventDate)
        tv_date.text = changeFormatDate(date)
        home_club.text = event.homeTeam
        away_club.text = event.awayTeam

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = event.eventName

        val apiMatchDetail = TheSportDBApi(event.eventId).getMatchDetail()
        val apiHomeTeam = TheSportDBApi(event.homeTeamId).getTeamDetail()
        val apiAwayTeam = TheSportDBApi(event.awayTeamId).getTeamDetail()
        val gson = Gson()
        presenter = EventDetailPresenter(this, apiMatchDetail, apiHomeTeam, apiAwayTeam, gson)
        presenter.getEventDetail()

        swipe_match.onRefresh {
            presenter.getEventDetail()
        }
        swipe_match.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
    }

    override fun showLoading() {
        swipe_match.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_match.isRefreshing = false
    }

    override fun showDetail(eventDetail: Event, homeTeam: Event, awayTeam: Event) {
        Picasso.get().load(homeTeam.teamBadge).into(home_img)
        home_club.text = eventDetail.homeTeam
        home_score.text = eventDetail.homeScore
        home_formation.text = eventDetail.homeFormation
        home_goals.text = if(eventDetail.homeGoalDetails.isNullOrEmpty()) "-" else eventDetail.homeGoalDetails?.replace(";", ";\n")
        home_shots.text = eventDetail.homeShots ?: "-"
        home_goalkeeper.text = if(eventDetail.homeLineupGoalKeeper.isNullOrEmpty()) "-" else eventDetail.homeLineupGoalKeeper?.replace("; ", ";\n")
        home_defense.text = if(eventDetail.homeLineupDefense.isNullOrEmpty()) "-" else eventDetail.homeLineupDefense?.replace("; ", ";\n")
        home_midfield.text = if(eventDetail.homeLineupMidfield.isNullOrEmpty()) "-" else eventDetail.homeLineupMidfield?.replace("; ", ";\n")
        home_forward.text = if(eventDetail.homeLineupForward.isNullOrEmpty()) "-" else eventDetail.homeLineupForward?.replace("; ", ";\n")
        home_subtitutes.text = if(eventDetail.homeLineupSubstitutes.isNullOrEmpty()) "-" else eventDetail.homeLineupSubstitutes?.replace("; ", ";\n")

        Picasso.get().load(awayTeam.teamBadge).into(away_img)
        away_club.text = eventDetail.awayTeam
        away_score.text = eventDetail.awayScore
        away_formation.text = eventDetail.awayFormation
        away_goals.text = if(eventDetail.awayGoalsDetails.isNullOrEmpty()) "-" else eventDetail.awayGoalsDetails?.replace(";", ";\n")
        away_shots.text = eventDetail.awayShots ?: "-"
        away_goalkeeper.text = if(eventDetail.awayLineupGoalKeeper.isNullOrEmpty()) "-" else eventDetail.awayLineupGoalKeeper?.replace("; ", ";\n")
        away_defense.text = if(eventDetail.awayLineupDefense.isNullOrEmpty()) "-" else eventDetail.awayLineupDefense?.replace("; ", ";\n")
        away_midfield.text = if(eventDetail.awayLineupMidfield.isNullOrEmpty()) "-" else eventDetail.awayLineupMidfield?.replace("; ", ";\n")
        away_forward.text = if(eventDetail.awayLineupForward.isNullOrEmpty()) "-" else eventDetail.awayLineupForward?.replace("; ", ";\n")
        away_subtitutes.text = if(eventDetail.awayLineupSubstitutes.isNullOrEmpty()) "-" else eventDetail.awayLineupSubstitutes?.replace("; ", ";\n")

        hideLoading()
    }

}