package com.firstzoom.sleep.sleeptracker

import android.app.Application
import androidx.lifecycle.*
import com.firstzoom.sleep.database.SleepDatabaseDao
import com.firstzoom.sleep.database.SleepNight
import com.firstzoom.sleep.formatNights
import com.firstzoom.sleep.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SleepTrackerViewModel @Inject constructor(private val repository: Repository,
                                                application: Application
) : AndroidViewModel(application) {

    private var tonight = MutableLiveData<SleepNight?>()
    private val nights = repository.getAllNights()
    private val _navigateToSleepQuality=MutableLiveData<SleepNight?>()
    val navigateToSleepQuality:LiveData<SleepNight?>
    get()=_navigateToSleepQuality

    /**
     * Converted nights to Spanned for displaying.
     */
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }
    fun doneNavigating(){
        _navigateToSleepQuality.value=null
    }
    /**
     *  Handling the case of the stopped app or forgotten recording,
     *  the start and end times will be the same.j
     *
     *  If the start time and end time are not the same, then we do not have an unfinished
     *  recording.
     */
    private suspend fun getTonightFromDatabase(): SleepNight? {
        var night = repository.getTonight()
        if (night?.endTimeMilli != night?.startTimeMilli) {
            night = null
        }
        return night
    }

    /**
     * Executes when the START button is clicked.
     */
    fun onStartTracking() {
        viewModelScope.launch {
            // Create a new night, which captures the current time,
            // and insert it into the database.
            val newNight = SleepNight()
            repository.insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    /**
     * Executes when the STOP button is clicked.
     */
    fun onStopTracking() {
        viewModelScope.launch {
            // In Kotlin, the return@label syntax is used for specifying which function among
            // several nested ones this statement returns from.
            // In this case, we are specifying to return from launch(),
            // not the lambda.
            val oldNight = tonight.value ?: return@launch

            // Update the night in the database to add the end time.
            oldNight.endTimeMilli = System.currentTimeMillis()

            repository.update(oldNight)
            _navigateToSleepQuality.value=oldNight
        }
    }

    /**
     * Executes when the CLEAR button is clicked.
     */
    fun onClear() {
        viewModelScope.launch {
            // Clear the database table.
            repository.clear()
            // And clear tonight since it's no longer in the database
            tonight.value = null
        }
    }

    /**
     */
}