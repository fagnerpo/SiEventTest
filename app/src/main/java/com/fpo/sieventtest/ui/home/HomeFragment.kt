package com.fpo.sieventtest.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.fpo.sieventtest.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeFragmentModelView by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = HomeAdapter { event ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(event)
            val navController = findNavController()
            navController.navigate(action)
        }

        viewModel.eventList.observe(viewLifecycleOwner, {
            adapter.setEvents(it)
        })

        binding.recyclerview.adapter = adapter

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBarHome.visibility = View.VISIBLE
            } else {
                binding.progressBarHome.visibility = View.GONE
            }
        })

        viewModel.getAllEvents()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}