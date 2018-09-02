package com.faisaluje.footballmatchschedule.model

data class ApiResponse(
    val events: List<MatchDetail>,
    val teams: List<MatchDetail>
)