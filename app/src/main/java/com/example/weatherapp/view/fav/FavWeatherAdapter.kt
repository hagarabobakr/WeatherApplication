package com.example.weatherapp.view.fav

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.model.FavoriteWeather
import com.example.weatherapp.databinding.FavItemBinding
import com.example.weatherapp.databinding.TodayItemBinding
import java.util.Date
import java.util.Locale

class FavWeatherAdapter (private val onItemClick: FavItemClickListener) :
    ListAdapter<FavoriteWeather, FavWeatherAdapter.ProductViewHolder>(FavProductDiffCallback()) {

    class ProductViewHolder(private val binding: FavItemBinding, private val onItemClick: FavItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: FavoriteWeather) {
            binding.townName.text = weather.name
            binding.date.text = "${formatDate(weather.dt)}"
            binding.temp.text = weather.temp.toString()
        }

        private fun formatDay(timestamp: Long): String {
            val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
            return sdf.format(Date(timestamp * 1000))
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            return sdf.format(Date(timestamp * 1000))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
       // val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_item, parent, false)
        val binding = FavItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FavProductDiffCallback : DiffUtil.ItemCallback<FavoriteWeather>() {

    override fun areItemsTheSame(oldItem: FavoriteWeather, newItem: FavoriteWeather): Boolean {
        return oldItem.name== newItem.name
    }

    override fun areContentsTheSame(oldItem: FavoriteWeather, newItem: FavoriteWeather): Boolean {
        return oldItem == newItem
    }
}