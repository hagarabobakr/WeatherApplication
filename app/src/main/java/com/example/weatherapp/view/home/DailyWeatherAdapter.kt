package com.example.weatherapp.view.home

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherForecast
import com.example.weatherapp.databinding.NextDayItemBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class DailyWeatherAdapter:ListAdapter<WeatherForecast,DailyWeatherAdapter.DailyWeatherViewHolder>(DailyWeatherDiffCallback()){


    class DailyWeatherViewHolder(private val binding: NextDayItemBinding): RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(weather: WeatherForecast) {
            // Convert dt_txt to LocalDateTime
            val dateTime = LocalDateTime.parse(weather.dt_txt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            // Get the name of the day
            val dayOfWeek = dateTime.dayOfWeek.name.toLowerCase(Locale.getDefault()).capitalize()
            binding.townName.text = dayOfWeek // Set the day name
            // Get a formatted date
            val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
            binding.date.text = dateTime.format(dateFormatter) // Set the formatted date (day/month/year)
            // Set the temperature
            binding.temp.text = weather.main.temp.toString()
            // Load weather icon
            Glide.with(binding.tempImg.context)
                .load("https://openweathermap.org/img/wn/${weather.weather[0].icon}.png")
                .into(binding.tempImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val binding = NextDayItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DailyWeatherViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}




class DailyWeatherDiffCallback : DiffUtil.ItemCallback<WeatherForecast>() {

    override fun areItemsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast): Boolean {
        return oldItem == newItem
    }
}
/*class ProductAdapter(private val onItemClick: ProductClickListner) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    class ProductViewHolder(itemView: View, private val onItemClick: ProductClickListner) :
        RecyclerView.ViewHolder(itemView) {
        private val productImage: ImageView = itemView.findViewById(R.id.productImage)
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productCategory: TextView = itemView.findViewById(R.id.productCategory)
        private val productDescription: TextView = itemView.findViewById(R.id.productDescription)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)

        fun bind(product: Product) {
            productName.text = product.title
            productCategory.text = product.category
            productDescription.text = product.description
            productPrice.text = "$${product.price}"  // Formatting price as currency

            // Use Glide or any other image loading library
            Glide.with(itemView.context)
                .load(product.thumbnail)
                .placeholder(R.drawable.ic_launcher_background)  // Placeholder image
                .into(productImage)

            itemView.setOnClickListener {
                onItemClick.onProductClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view,onItemClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
*/