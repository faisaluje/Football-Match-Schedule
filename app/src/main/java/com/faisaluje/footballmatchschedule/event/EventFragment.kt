package com.faisaluje.footballmatchschedule.event

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
import com.faisaluje.footballmatchschedule.api.ApiRepository
import com.faisaluje.footballmatchschedule.detail.EventDetailActivity
import com.faisaluje.footballmatchschedule.model.Event
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class EventFragment: Fragment(), AnkoComponent<Context>, EventView{
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: EventPresenter
    private lateinit var eventList: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: EventAdapter
    private var fixture = 1
    private var leagueId = "4328"   //EPL

    companion object {
        fun newFragment(fixture: Int, leagueId: String): EventFragment {
            val fragment = EventFragment()
            fragment.fixture = fixture
            fragment.leagueId = leagueId

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventPresenter(this, request, gson, fixture)

        adapter = EventAdapter(events){
            ctx.startActivity<EventDetailActivity>("EVENT" to it)
        }
        eventList.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getList(leagueId)
        }

        presenter.getList(leagueId)
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

                eventList = recyclerView {
                    id = R.id.rv_event_list
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }

        }
    }
}