package com.faisaluje.footballmatchschedule.event

import com.faisaluje.footballmatchschedule.TestContextProvider
import com.faisaluje.footballmatchschedule.api.ApiRepository
import com.faisaluje.footballmatchschedule.api.TheSportDBApi
import com.faisaluje.footballmatchschedule.model.MatchResponse
import com.faisaluje.footballmatchschedule.model.Event
import com.faisaluje.footballmatchschedule.model.Team
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EventPresenterTest {
    @Mock
    private lateinit var view: EventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        presenter = EventPresenter(
                view,
                apiRepository,
                gson,
                1,
                TestContextProvider()
        )
    }

    @Test
    fun testGetEventDetail() {
        val events: MutableList<Event> = mutableListOf()
        val leagueId = "4328"
        val response = MatchResponse(events)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPrevSchedule(leagueId)),
                MatchResponse::class.java
        )).thenReturn(response)

        presenter.getList(leagueId)

        verify(view).showLoading()
        verify(view).showList(response.events)
        verify(view).hideLoading()
    }
}