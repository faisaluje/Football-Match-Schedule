package com.faisaluje.footballmatchschedule.detail

import com.faisaluje.footballmatchschedule.TestContextProvider
import com.faisaluje.footballmatchschedule.api.TheSportDBApi
import com.faisaluje.footballmatchschedule.model.Event
import com.faisaluje.footballmatchschedule.model.Team
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EventDetailPresenterTest {
    @Mock
    private lateinit var view: EventDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiMatchDetail: TheSportDBApi

    @Mock
    private lateinit var apiHomeTeam: TheSportDBApi

    @Mock
    private lateinit var apiAwayTeam: TheSportDBApi

    @Mock
    private lateinit var presenter: EventDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        apiMatchDetail = TheSportDBApi("576492")
        apiHomeTeam = TheSportDBApi("133604")
        apiAwayTeam = TheSportDBApi("133604")
        presenter = EventDetailPresenter(
                view,
                apiMatchDetail.getMatchDetail(),
                apiHomeTeam.getTeamDetail(),
                apiAwayTeam.getTeamDetail(),
                gson,
                TestContextProvider()
        )
    }

    @Test
    fun testGetEventDetail() {
        val event = Event()
        val homeTeam = Team()
        val awayTeam = Team()

        presenter.getEventDetail()

        verify(view).showLoading()
        verify(view).showDetail(event, homeTeam, awayTeam)
        verify(view).hideLoading()
    }
}