package com.fpo.sieventtest.ui.checkin

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fpo.sieventtest.databinding.CheckInFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckInFragment : Fragment() {

    private val args: CheckInFragmentArgs by navArgs()
    private var _binding: CheckInFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setEvent(args.eventData)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CheckInFragmentBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            val name = binding.textInputEditTextName.text.toString()
            val mail = binding.textInputEditTextMail.text.toString()
            activity?.let { act -> hideKeyboard(act) }
            viewModel.postCheckIn(mail, name)
        }

        viewModel.messageWarnings.observe(viewLifecycleOwner, {
            binding.textViewWarning.text = it
        })

        viewModel.messageToast.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBarCheckIn.visibility = View.VISIBLE
            } else {
                binding.progressBarCheckIn.visibility = View.GONE
            }
        })

        viewModel.successCheckIn.observe(viewLifecycleOwner, {
            val action = CheckInFragmentDirections.actionCheckInFragmentToHomeFragment()
            val navController = findNavController()
            navController.navigate(action)
        })

        return binding.root
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}