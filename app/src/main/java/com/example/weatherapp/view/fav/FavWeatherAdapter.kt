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

class FavWeatherAdapter (private val onItemClick: FavItemClickListener,private val selectedLanguage: String) :
    ListAdapter<FavoriteWeather, FavWeatherAdapter.ProductViewHolder>(FavProductDiffCallback()) {

    class ProductViewHolder(private val binding: FavItemBinding, private val onItemClick: FavItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: FavoriteWeather, locale: Locale) {
            binding.townName.text = weather.name
            binding.date.text = formatDate(weather.dt, locale)
            binding.temp.text = weather.temp.toString()

            Glide.with(binding.tempImg.context)
                .load(weather.icon)
                .into(binding.tempImg)

            binding.deleteIc.setOnClickListener {
                onItemClick.onDeleteIconClicked(weather)
            }
            binding.root.setOnClickListener {
                onItemClick.onItemClicked(weather)
            }
        }

        private fun formatDate(timestamp: Long, locale: Locale): String {
            val sdf = SimpleDateFormat("dd MMMM yyyy", locale)
            return sdf.format(Date(timestamp * 1000))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
       // val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_item, parent, false)
        val binding = FavItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val locale = if (selectedLanguage == "ar") Locale("ar") else Locale("en")
        holder.bind(getItem(position),locale)
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