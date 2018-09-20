package com.faisaluje.footballmatchschedule.api

import org.junit.Test

import org.junit.Assert.*

class TheSportDBApiTest {

    @Test
    fun testGetTeamDetail() {
        val url = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=133604"


        assertEquals(url, TheSportDBApi.getTeamDetail("133604"))
    }
}