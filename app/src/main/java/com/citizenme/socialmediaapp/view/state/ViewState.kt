package com.citizenme.socialmediaapp.view.state

sealed class ViewState<T> {
    class Success<T>(val data: T) : ViewState<T>()
    class Error<T>(val message: String?) : ViewState<T>()
    class Loading<T> : ViewState<T>()
}
