package com.firstzoom.sleep.sleepquality

import androidx.lifecycle.*
import com.firstzoom.sleep.database.SleepDatabaseDao
import com.firstzoom.sleep.database.SleepNight
import com.firstzoom.sleep.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SleepQualityViewModel
@Inject
constructor( private val repository: Repository,savedStateHandle: SavedStateHandle) :
    ViewModel() {
    private val nightKey: Long = savedStateHandle["sleepNightKey"]
        ?: throw IllegalArgumentException("Night ID required")
    var _navigateToSleepTracker = MutableLiveData<Boolean?>()
    // var nightKey:Long=0L

    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

 /*   fun onSetQuality(quality: Int) {
        viewModelScope.launch {
            var night = database.get(nightKey) ?: return@launch
            night?.sleepQuality = quality
            database.update(night)
            _navigateToSleepTracker.value = true
        }
    }
*/
 fun onSetQuality(quality: Int) {
     viewModelScope.launch {
         var night:SleepNight = repository.getNight(nightKey) ?: return@launch
         night?.sleepQuality = quality
         repository.update(night)
         _navigateToSleepTracker.value = true
     }
 }


}