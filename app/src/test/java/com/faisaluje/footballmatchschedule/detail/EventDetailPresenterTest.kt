package com.faisaluje.footballmatchschedule.detail

import com.faisaluje.footballmatchschedule.TestContextProvider
import com.faisaluje.footballmatchschedule.api.ApiRepository
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
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        presenter = EventDetailPresenter(
                view,
                apiRepository,
                gson,
                TestContextProvider()
        )
    }

    @Test
    fun testGetEventDetail() {
        val event = Event()
        val homeTeam = Team()
        val awayTeam = Team()
        val eventId = "576492"
        val homeTeamId = "133604"
        val awayTeamId = "133604"

        presenter.getEventDetail(eventId, homeTeamId, awayTeamId)

        verify(view).showLoading()
        verify(view).showDetail(event, homeTeam, awayTeam)
        verify(view).hideLoading()
    }
}