package com.example.tracker.workouts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tracker.common.AppDatabase
import com.example.tracker.common.AppRepository
import com.example.tracker.common.entities.UserAndWorkoutsAndEntries

class WorkoutsViewModel(application: Application) : AndroidViewModel(application) {

    private val appRepository: AppRepository
    val workouts: LiveData<List<UserAndWorkoutsAndEntries>>

    init {
        var usersDao = AppDatabase.getDatabase(application, viewModelScope).usersDao();
        var exercisesDao = AppDatabase.getDatabase(application, viewModelScope).exercisesDao();
        appRepository = AppRepository(usersDao, exercisesDao)
        workouts = appRepository.workouts
    }
}