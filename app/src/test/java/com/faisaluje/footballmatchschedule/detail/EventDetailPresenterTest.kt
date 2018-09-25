package com.faisaluje.footballmatchschedule.detail

import com.faisaluje.footballmatchschedule.TestContextProvider
import com.faisaluje.footballmatchschedule.api.ApiRepository
import com.faisaluje.footballmatchschedule.api.TheSportDBApi
import com.faisaluje.footballmatchschedule.model.MatchResponse
import com.faisaluje.footballmatchschedule.model.TeamResponse
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EventDetailPresenterTest {
    @Mock
    private lateinit var view: EventDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var matchResponse: MatchResponse

    @Mock
    private lateinit var homeTeamResponse: TeamResponse

    @Mock
    private lateinit var awayTeamResponse: TeamResponse

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
        val eventId = "576492"
        val homeTeamId = "134777"
        val awayTeamId = "133610"

        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getMatchDetail(eventId)), MatchResponse::class.java)).thenReturn(matchResponse)
        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(homeTeamId)), TeamResponse::class.java)).thenReturn(homeTeamResponse)
        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(awayTeamId)), TeamResponse::class.java)).thenReturn(awayTeamResponse)

        presenter.getEventDetail(eventId, homeTeamId, awayTeamId)

        verify(view).showLoading()
        verify(view).showDetail(matchResponse.events, homeTeamResponse.teams, awayTeamResponse.teams)
        verify(view).hideLoading()
    }
}