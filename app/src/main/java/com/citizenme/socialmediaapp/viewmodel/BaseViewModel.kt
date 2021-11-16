package com.citizenme.socialmediaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.citizenme.socialmediaapp.view.state.ViewState

abstract class BaseViewModel : ViewModel() {

    val viewState = MutableLiveData<ViewState<*>>()

}