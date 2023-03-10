package com.firstzoom.sleep.sleeptracker

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.firstzoom.sleep.R
import com.firstzoom.sleep.database.SleepDatabase
import com.firstzoom.sleep.databinding.FragmentSleepTrackerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SleepTrackerFragment : Fragment() {

    companion object {
        fun newInstance() = SleepTrackerFragment()
    }

    private lateinit var viewModel: SleepTrackerViewModel
    private lateinit var  binding: FragmentSleepTrackerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding = FragmentSleepTrackerBinding.inflate(
            inflater, container, false)

        val application = requireNotNull(this.activity).application

      //  val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        //val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProvider(
                this).get(SleepTrackerViewModel::class.java)
        setListeners()

        return binding.root
    }

    private fun setListeners() {

        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer {sleepNight->
            sleepNight?.let { this.findNavController().navigate(SleepTrackerFragmentDirections.
            actionSleepTrackerFragmentToSleepQualityFragment(sleepNight.nightId))
            viewModel.doneNavigating()
            }
        })
        viewModel.nightsString.observe(viewLifecycleOwner, Observer { text-> binding.textview.setText(text)})
        binding.startButton.setOnClickListener { view->
            viewModel.onStartTracking()
         }
        binding.stopButton.setOnClickListener { view-> viewModel.onStopTracking() }
        binding.clearButton.setOnClickListener { view->viewModel.onClear() }
    }
}