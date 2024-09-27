package com.example.weatherapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.model.WeatherRepository
import com.example.weatherapp.data.remot.WeatherRemoteDataSource
import com.example.weatherapp.data.sharedprefrances.GlobalSharedPreferenceDataSourceImp
import com.example.weatherapp.databinding.FragmentMapBinding
import com.example.weatherapp.viewmodel.home.HomeFragmentViewModelFactory
import com.example.weatherapp.viewmodel.map.MapViewModel
import com.example.weatherapp.viewmodel.map.MapViewModelFactory
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var viewModel: MapViewModel
    // DataSources and Repository initialization
    private val remoteDataSource = WeatherRemoteDataSource()
    private val localDataSource = WeatherLocalDataSource()
    private lateinit var sharedPreferenceDataSourceImp: GlobalSharedPreferenceDataSourceImp
    private lateinit var repository: WeatherRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SharedPreferences and Repository
        sharedPreferenceDataSourceImp = GlobalSharedPreferenceDataSourceImp(
            requireActivity().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        )
        repository = WeatherRepository.getInstance(remoteDataSource, localDataSource, sharedPreferenceDataSourceImp)

        // Initialize ViewModel
        val factory = MapViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MapViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// Initialize the MapView
        mapView = binding.map
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(8.0)
        val sourceFragment = arguments?.getString("sourceFragment")

        when (sourceFragment) {
            "SettingsFragment" -> {
                addMapClickListener()
            }
            "HomeFragment" -> {
                // إجراء معين عند الانتقال من HomeFragment
            }
            "FavoriteFragment" -> {
                // إجراء معين عند الانتقال من FavoriteFragment
            }
        }
        // Initialize osmdroid configuration
        Configuration.getInstance().load(requireContext(), requireContext().getSharedPreferences("osmdroid", 0))


        //addMapClickListener()
    }
    private fun addMapClickListener() {
        val mapEventsReceiver = object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                p?.let {
                    val lat = it.latitude
                    val lon = it.longitude
                    showConfirmationDialog(lat, lon)
//                    addMarker(it)
//                    viewModel.saveLocation(lat, lon) // Save location using ViewModel
//                    findNavController().navigate(R.id.action_mapFragment_to_homeFragment2)
                }
                return true
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                return false
            }
        }

        val overlayEvents = MapEventsOverlay(mapEventsReceiver)
        mapView.overlays.add(overlayEvents)
    }
    private fun addMarker(point: GeoPoint) {
        val marker = Marker(mapView)
        marker.position = point
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Selected Location"
        marker.setOnMarkerClickListener { m, mapView ->
            InfoWindow.closeAllInfoWindowsOn(mapView)
            m.showInfoWindow()
            true
        }
        mapView.overlays.add(marker)
        mapView.invalidate()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    private fun showConfirmationDialog(lat: Double, lon: Double) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_dialog, null)
        builder.setView(dialogView)

        val titleView: TextView = dialogView.findViewById(R.id.dialog_title)
        val messageView: TextView = dialogView.findViewById(R.id.dialog_message)
        val yesButton: Button = dialogView.findViewById(R.id.button_yes)
        val noButton: Button = dialogView.findViewById(R.id.button_no)

        titleView.text = "Confirmation"
        messageView.text = "Are you sure you want to go to this location?"

        val dialog = builder.create()

        yesButton.setOnClickListener {
            addMarker(GeoPoint(lat, lon))
            viewModel.saveLocation(lat, lon) // Save location using ViewModel
            findNavController().navigate(R.id.action_mapFragment_to_homeFragment2)
            dialog.dismiss()
        }

        noButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}