package com.fpo.sieventtest.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fpo.sieventtest.R
import com.fpo.sieventtest.databinding.DetailsFragmentBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel()
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.eventData?.let { viewModel.setEvent(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        binding.mapEvent.onCreate(savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.eventManager.observe(viewLifecycleOwner, {
            binding.textViewTitle.text = it.title
            binding.textViewAmount.text = it.price
            binding.textViewDate.text = it.data
            binding.textViewDescription.text = it.description
            Picasso.get().load(it.image).error(R.drawable.ic_warning_image).into(binding.imageViewDetails)

            binding.mapEvent.getMapAsync { googleMap ->
                val eventLocation = LatLng(it.latitude, it.longitude)
                googleMap.addMarker(MarkerOptions().position(eventLocation).title(it.title))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 14F))
            }
        })

        viewModel.dataToShare.observe(viewLifecycleOwner, {
            startActivity(Intent.createChooser(it, null))
        })

        binding.buttonShare.setOnClickListener {
            viewModel.shareEvent()
        }

        viewModel.checkin.observe(viewLifecycleOwner, {
            val navController = findNavController()
            val action = DetailsFragmentDirections.actionDetailsFragmentToCheckInFragment(it)
            navController.navigate(action)
        })

        binding.buttonCheck.setOnClickListener {
            viewModel.checkin()
        }
    }

    override fun onStart() {
        binding.mapEvent.onStart()
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        super.onDestroy()

        _binding = null
    }

    override fun onResume() {
        binding.mapEvent.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapEvent.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapEvent.onLowMemory()
    }
}