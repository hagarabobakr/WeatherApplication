package com.example.weatherapp.view.fav

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.local.AppDatabase
import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.remot.WeatherRemoteDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSourceImp
import com.example.weatherapp.databinding.FragmentFavoriteBinding
import com.example.weatherapp.viewmodel.fav.FavoriteViewModel
import com.example.weatherapp.viewmodel.fav.FavoriteViewModelFactory

class FavoriteFragment : Fragment() {
    lateinit var binding : FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favFactory: FavoriteViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_favorite, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()

        binding.floatingActionButton.setOnClickListener {
        val bundle = Bundle().apply {
            putString("sourceFragment", "FavoriteFragment")
        }
        findNavController().navigate(R.id.action_favoriteFragment2_to_mapFragment, bundle)
    }
    }


    private fun setupViewModel() {
        val sharedPrefs = requireActivity().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val remoteDataSource = WeatherRemoteDataSource()
        val database = AppDatabase.getDatabase(requireContext())
        val localDataSource = WeatherLocalDataSource(database.favoriteWeatherDao())
        val sharedPreferenceDataSourceImp = GlobalSharedPreferenceDataSourceImp(sharedPrefs)
        val repository = WeatherRepository.getInstance(remoteDataSource, localDataSource, sharedPreferenceDataSourceImp)

        favFactory = FavoriteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, favFactory).get(FavoriteViewModel::class.java)
    }
}