package com.citizenme.socialmediaapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.citizenme.socialmediaapp.utils.CustomSharedPreferences
import com.citizenme.socialmediaapp.view.state.ViewState

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val viewState = MutableLiveData<ViewState<*>>()
    protected val customPrefs = CustomSharedPreferences(application)

}