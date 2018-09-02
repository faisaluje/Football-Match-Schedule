package com.faisaluje.footballmatchschedule.schedule

import com.faisaluje.footballmatchschedule.base.BasePresenter
import com.faisaluje.footballmatchschedule.base.BaseView
import com.google.gson.Gson

class SchedulePresenter(view: BaseView, api: String, gson: Gson) : BasePresenter(view, api, gson)