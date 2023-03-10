package com.firstzoom.sleep.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.firstzoom.sleep.database.SleepDatabaseDao
import com.firstzoom.sleep.database.SleepNight
import kotlinx.coroutines.launch

class Repository(private val database: SleepDatabaseDao) {

    suspend fun getNight(nightKey: Long): SleepNight? {
        return database.get(nightKey)
    }

   suspend fun update(night: SleepNight) {
        database.update(night)

    }

    fun getAllNights(): LiveData<List<SleepNight>> {
        return database.getAllNights()

    }

   suspend fun getTonight(): SleepNight? {
        return database.getTonight()
   }
    public suspend fun clear() {
        database.clear()
    }



    public suspend fun insert(night: SleepNight) {
        database.insert(night)
    }
}