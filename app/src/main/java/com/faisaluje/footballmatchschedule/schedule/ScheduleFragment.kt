package com.faisaluje.footballmatchschedule.schedule

import android.content.Context
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
import com.faisaluje.footballmatchschedule.main.DetailActivity
import com.faisaluje.footballmatchschedule.model.Event
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class ScheduleFragment: Fragment(), AnkoComponent<Context>, ScheduleView{
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: SchedulePresenter
    private lateinit var scheduleList: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: ScheduleAdapter
    private var fixture = 1
    private var leagueId = "4328"   //EPL

    companion object {
        fun newFragment(fixture: Int, leagueId: String): ScheduleFragment {
            val fragment = ScheduleFragment()
            fragment.fixture = fixture
            fragment.leagueId = leagueId

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val theSportDBApi = TheSportDBApi(leagueId)
        val api = if (fixture == 1) theSportDBApi.getPrevSchedule() else theSportDBApi.getNextSchedule()
        val gson = Gson()
        presenter = SchedulePresenter(this, api, gson)

        adapter = ScheduleAdapter(events){
            ctx.startActivity<DetailActivity>("EVENT" to it)
        }
        scheduleList.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getList()
        }

        presenter.getList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showList(data: List<Event>) {
        hideLoading()
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun createView(ui: AnkoContext<Context>) = with(ui){
        verticalLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                scheduleList = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }

        }
    }
}