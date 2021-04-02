package com.github.kotlinapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.kotlinapp.R
import com.github.kotlinapp.domain.model.Forecast
import com.github.kotlinapp.domain.model.ForecastList
import com.github.kotlinapp.extensions.ctx
import com.github.kotlinapp.extensions.toDefaultString
import org.jetbrains.anko.find
import java.text.DateFormat
import java.util.*

class ForecastListAdapter(
    private val weekForecast: ForecastList,
    private val itemClick: (Forecast) -> Unit
) :
    RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    override fun getItemCount(): Int = weekForecast.size

    class ViewHolder(view: View, private val itemClick: (Forecast) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val iconView = view.find<ImageView>(R.id.icon)
        private val dateView = view.find<TextView>(R.id.date)
        private val descriptionView = view.find<TextView>(R.id.description)
        private val maxTemperatureView = view.find<TextView>(R.id.maxTemperature)
        private val minTemperatureView = view.find<TextView>(R.id.minTemperature)


        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Glide.with(iconView.ctx).load(iconUrl).into(iconView)
                dateView.text = date.toDefaultString()
                descriptionView.text = description
                maxTemperatureView.text = "${high}º"
                minTemperatureView.text = "${low}º"
                itemView.setOnClickListener {
                    itemClick.invoke(this)
                }
            }
        }
    }

}