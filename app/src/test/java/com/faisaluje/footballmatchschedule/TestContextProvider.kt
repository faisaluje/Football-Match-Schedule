package com.faisaluje.footballmatchschedule

import com.faisaluje.footballmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider: CoroutineContextProvider(){
    override val main: CoroutineContext = Unconfined
}