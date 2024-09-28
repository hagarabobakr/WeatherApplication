package com.example.weatherapp.view.fav

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.local.AppDatabase
import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.remot.ApiState
import com.example.weatherapp.data.remot.WeatherRemoteDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSourceImp
import com.example.weatherapp.databinding.FragmentFavoriteBinding
import com.example.weatherapp.viewmodel.fav.FavoriteViewModel
import com.example.weatherapp.viewmodel.fav.FavoriteViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(),FavItemClickListener {
    private  val TAG = "FavoriteFragment"
    lateinit var binding : FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favFactory: FavoriteViewModelFactory
    private lateinit var favWeatherAdapter: FavWeatherAdapter
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
        setupRecyclerView()
        viewModel.localWeather.observe(viewLifecycleOwner){weather->
            favWeatherAdapter.submitList(weather)

        }
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
    private fun setupRecyclerView() {
        favWeatherAdapter = FavWeatherAdapter(this)
        binding.recyclerView3.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = favWeatherAdapter
        }
    }


}