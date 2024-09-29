package com.example.weatherapp.view.fav

import com.example.weatherapp.data.model.FavoriteWeather

interface FavItemClickListener {
   fun  onDeleteIconClicked(favItem:FavoriteWeather)
   fun  onItemClicked(favItem:FavoriteWeather)
}