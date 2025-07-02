package com.example.matchmate.ui

import androidx.lifecycle.ViewModel
import com.example.matchmate.data.local.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

}