package com.faisaluje.footballmatchschedule.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.faisaluje.footballmatchschedule.R
import com.faisaluje.footballmatchschedule.R.id.*
import com.faisaluje.footballmatchschedule.R.layout.activity_main
import com.faisaluje.footballmatchschedule.event.EventFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var leagueId = "4328"   //EPL
    private var fixture = 1
    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        setContentView(activity_main)

        nav_button.setOnNavigationItemSelectedListener {
            item -> when(item.itemId){
                navigation_prev -> {
                    fixture = 1
                    openFragment(EventFragment.newFragment(fixture, leagueId))
                    return@setOnNavigationItemSelectedListener true
                }
                navigation_next -> {
                    fixture = 2
                    openFragment(EventFragment.newFragment(fixture, leagueId))
                    return@setOnNavigationItemSelectedListener true
                }
                navigation_favorite -> {

                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        nav_button.selectedItemId = navigation_prev
    }

    private fun openFragment(fragment: Fragment){
        if(savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragment, fragment.javaClass.simpleName)
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.league, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            menu_1 -> {
                leagueId = "4328"
            }
            menu_2 -> {
                leagueId = "4331"
            }
            menu_3 -> {
                leagueId = "4332"
            }
            menu_4 -> {
                leagueId = "4334"
            }
            menu_5 -> {
                leagueId = "4335"
            }
        }
        openFragment(EventFragment.newFragment(fixture, leagueId))
        return super.onOptionsItemSelected(item)
    }
}
