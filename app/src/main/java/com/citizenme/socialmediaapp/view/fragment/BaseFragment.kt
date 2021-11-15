package com.citizenme.socialmediaapp.view.fragment

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(){

    abstract fun observeViewState()
    abstract fun onErrorRefreshClick()
}