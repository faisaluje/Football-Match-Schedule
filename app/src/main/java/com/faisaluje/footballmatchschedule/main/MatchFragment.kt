package com.faisaluje.footballmatchschedule.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faisaluje.footballmatchschedule.R
import com.faisaluje.footballmatchschedule.api.TheSportDBApi
import com.faisaluje.footballmatchschedule.model.ApiResponse
import com.faisaluje.footballmatchschedule.model.Event
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class MatchFragment: Fragment() {
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var rv: RecyclerView
    private lateinit var swipe: SwipeRefreshLayout
    private var fixture = 1
    private var leagueId = "4328"   //EPL

    companion object {
        fun newInstance(fixture: Int, leagueId: String): MatchFragment{
            val fragment = MatchFragment()
            fragment.fixture = fixture
            fragment.leagueId = leagueId

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.match_list, container, false)

        rv = view.find(R.id.rv_match)
        swipe = view.find(R.id.swipe_schedule)

        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = MatchAdapter(events){
            startActivity<DetailActivity>("MATCH" to it)
        }

        getData()

        swipe.setColorSchemeResources(R.color.colorAccent)
        swipe.onRefresh {
            getData()
        }

        return view
    }

    private fun getData(){
        swipe.isRefreshing = true
        doAsync {
            val api = TheSportDBApi(leagueId)

            val json = if (fixture == 1) api.getPrevSchedule() else api.getNextSchedule()
            val data = Gson().fromJson(json, ApiResponse::class.java)

            uiThread{
                swipe.isRefreshing = false
                events.clear()
                events.addAll(data.events)
                rv.adapter?.notifyDataSetChanged()
            }
        }
    }
}