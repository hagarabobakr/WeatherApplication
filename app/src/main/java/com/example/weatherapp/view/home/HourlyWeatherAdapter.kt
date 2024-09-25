package com.example.weatherapp.view.home

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast
import com.example.weatherapp.databinding.TodayItemBinding
import java.util.Date
import java.util.Locale

class HourlyWeatherAdapter : ListAdapter<WeatherForecast, HourlyWeatherAdapter.HourlyWeatherViewHolder>
    (HourlyWeatherDiffCallback()) {

    class HourlyWeatherViewHolder(private val binding: TodayItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherForecast: WeatherForecast) {
            binding.textView3.text = formatTime(weatherForecast.dt)
            binding.textView4.text = weatherForecast.main.temp.toString()

            Glide.with(binding.imageView6.context)
                .load("https://openweathermap.org/img/wn/${weatherForecast.weather[0].icon}.png")
                .into(binding.imageView6)
        }

        private fun formatTime(timestamp: Long): String {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            return sdf.format(Date(timestamp * 1000))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val binding = TodayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class HourlyWeatherDiffCallback : DiffUtil.ItemCallback<WeatherForecast>() {
    override fun areItemsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast): Boolean {
        return oldItem == newItem
    }
}