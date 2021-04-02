package com.github.kotlinapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kotlinapp.R
import com.github.kotlinapp.domain.commands.RequestForecastCommand
import com.github.kotlinapp.extensions.DelegatesExt
import com.github.kotlinapp.ui.adapter.ForecastListAdapter
import kotlinx.coroutines.launch
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    private val zipCode: Long by DelegatesExt.preference(
        this, SettingActivity.ZIP_CODE,
        SettingActivity.DEFAULT_ZIP
    )

    private val forecastList by lazy { find<RecyclerView>(R.id.forecastList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }


    private fun loadForecast() = doAsync {
        val result = RequestForecastCommand(zipCode).execute()
        uiThread {
            val adapter = ForecastListAdapter(result) {
                startActivity<DetailActivity>(
                    DetailActivity.ID to it.id,
                    DetailActivity.CITY_NAME to result.city
                )
            }
            forecastList.adapter = adapter
            toolbarTitle = "${result.city}(${result.country})"
        }
    }
}