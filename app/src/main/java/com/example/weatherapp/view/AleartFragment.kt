package com.example.weatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentAleartBinding


class AleartFragment : Fragment() {
    private lateinit var binding: FragmentAleartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_aleart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            showDialog()
        }
    }
    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.activate_feature))
        builder.setMessage(R.string.activating_this_feature_costs_half_a_million)
        builder.setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

}