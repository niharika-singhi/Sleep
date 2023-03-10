package com.firstzoom.sleep.sleepquality

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
import com.firstzoom.sleep.databinding.FragmentSleepQualityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SleepQualityFragment : Fragment() {

    companion object {
        fun newInstance() = SleepQualityFragment()
    }

    private lateinit var viewModel: SleepQualityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSleepQualityBinding = FragmentSleepQualityBinding.inflate(
            inflater, container, false)
        val application = requireNotNull(this.activity).application
        val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())

        // Create an instance of the ViewModel Factory.
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
       // val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)
        viewModel=
            ViewModelProvider(
                this).get(SleepQualityViewModel::class.java)
       // viewModel.nightKey=arguments.sleepNightKey
        binding.qualityZeroImage.setOnClickListener { view-> viewModel.onSetQuality(0) }

        viewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if(it==true)
                this.findNavController().navigate(R.id.action_sleepQualityFragment_to_sleepTrackerFragment)
        })
        return binding.root

    }



}