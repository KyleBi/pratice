package com.github.kotlinapp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.github.kotlinapp.R
import com.github.kotlinapp.domain.commands.RequestDayForecastCommand
import com.github.kotlinapp.domain.model.Forecast
import com.github.kotlinapp.extensions.color
import com.github.kotlinapp.extensions.textColor
import com.github.kotlinapp.extensions.toCustomString
import com.github.kotlinapp.extensions.toDefaultString
import kotlinx.coroutines.launch
import org.jetbrains.anko.ctx
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.text.DateFormat

class DetailActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object {
        const val ID = "DetailActivity:id"
        const val CITY_NAME = "DetailActivity:cityName"
    }

    lateinit var icon: ImageView
    lateinit var weatherDescription: TextView
    lateinit var maxTemperature: TextView
    lateinit var minTemperature: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()
        toolbarTitle = intent.getStringExtra(CITY_NAME)!!
        icon = find(R.id.icon)
        weatherDescription = find(R.id.weatherDescription)
        maxTemperature = find(R.id.maxTemperature)
        minTemperature = find(R.id.minTemperature)
        enableHomeAsUp { onBackPressed() }
        doAsync {
            val result = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
            uiThread {
                bindForecast(result)
            }
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Glide.with(this@DetailActivity).load(iconUrl).into(icon)
        toolbar.subtitle = date.toDefaultString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    @SuppressLint("SetTextI18n")
    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}º"
        it.second.textColor = color(
            when (it.first) {
                in -50..0 -> android.R.color.holo_red_dark
                in 0..15 -> android.R.color.holo_orange_dark
                else -> android.R.color.holo_green_dark
            }
        )
    }
}