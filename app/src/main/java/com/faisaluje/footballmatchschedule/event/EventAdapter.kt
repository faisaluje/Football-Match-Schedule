package com.faisaluje.footballmatchschedule.event

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.faisaluje.footballmatchschedule.R
import com.faisaluje.footballmatchschedule.R.color.colorPrimary
import com.faisaluje.footballmatchschedule.model.Event
import com.faisaluje.footballmatchschedule.util.changeFormatDate
import com.faisaluje.footballmatchschedule.util.strToDate
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

class EventAdapter(private val events: List<Event>, private val listener: (Event) -> Unit): RecyclerView.Adapter<EventAdapter.EventViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(EventUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }


    class EventViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val eventDate: TextView = itemView.find(R.id.date)
        private val homeTeam: TextView = itemView.find(R.id.homeTeam)
        private val homeScore: TextView = itemView.find(R.id.homeScore)
        private val awayTeam: TextView = itemView.find(R.id.awayTeam)
        private val awayScore: TextView = itemView.find(R.id.awayScore)

        fun bindItem(event: Event, listener: (Event) -> Unit){
            val date = strToDate(event.eventDate)
            eventDate.text = changeFormatDate(date)

            homeTeam.text = event.homeTeam
            homeScore.text = event.homeScore

            awayTeam.text = event.awayTeam
            awayScore.text = event.awayScore

            itemView.onClick { listener(event) }
        }
    }

    class EventUI: AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui){
            cardView {
                lparams(width = matchParent, height = wrapContent){
                    gravity = Gravity.CENTER
                    margin = dip(4)
                    radius = 4f
                }

                verticalLayout {

                    textView("Minggu, 04 Maret 2018"){
                        id = R.id.date
                        textColorResource = colorPrimary
                    }.lparams(width = wrapContent, height = wrapContent){
                        gravity = Gravity.CENTER
                        margin = dip(8)
                    }

                    relativeLayout {

                        textView{
                            id = R.id.homeTeam
                            textSize = 14f
                            textColor = Color.BLACK
                            gravity = Gravity.END
                        }.lparams(width = wrapContent, height = wrapContent){
                            leftOf(R.id.homeScore)
                            rightMargin = dip(10)
                        }

                        textView{
                            id = R.id.homeScore
                            textSize = 12f
                            gravity = Gravity.CENTER
                            textColor = Color.BLACK
                        }.lparams(width = wrapContent, height = wrapContent){
                            leftOf(R.id.vs)
                        }

                        textView("vs"){
                            id = R.id.vs
                            textSize = 10f
                        }.lparams(width = wrapContent, height = wrapContent){
                            centerInParent()
                            leftMargin = dip(6)
                            rightMargin = dip(6)
                        }

                        textView{
                            id = R.id.awayScore
                            textSize = 12f
                            gravity = Gravity.CENTER
                            textColor = Color.BLACK
                        }.lparams(width = wrapContent, height = wrapContent){
                            rightOf(R.id.vs)
                        }

                        textView{
                            id = R.id.awayTeam
                            textSize = 14f
                            textColor = Color.BLACK
                            gravity = Gravity.START
                        }.lparams(width = wrapContent, height = wrapContent){
                            rightOf(R.id.awayScore)
                            leftMargin = dip(10)
                        }

                    }.lparams(width = matchParent, height = wrapContent)

                }.lparams(width = matchParent, height = wrapContent){
                    gravity = Gravity.CENTER
                    bottomMargin = dip(8)
                }
            }
        }

    }
}