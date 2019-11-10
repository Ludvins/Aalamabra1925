package com.example.aaalamabra1925.ui.interest_point

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InterestPointViewModel : ViewModel() {

    private val _title = MutableLiveData<String>().apply {
        value = "This is the Title"
    }
    private val _content = MutableLiveData<String>().apply {
        value = "This is the Content"
    }
    var title: LiveData<String> = _title
    var content: LiveData<String> = _content
}