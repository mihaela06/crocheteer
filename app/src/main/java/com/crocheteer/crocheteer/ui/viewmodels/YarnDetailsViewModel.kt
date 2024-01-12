package com.crocheteer.crocheteer.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crocheteer.crocheteer.data.YarnStashRepository
import com.crocheteer.crocheteer.data.entities.YarnType
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YarnDetailsViewModel @Inject constructor(
    private val yarnStashRepository: YarnStashRepository
) : ViewModel() {
    var yarn by mutableStateOf<YarnTypeWithColors?>(null)
        private set

    fun getYarn(id: Long) = viewModelScope.launch {
        yarn = yarnStashRepository.getStashedYarn(id).first()
    }

    fun deleteYarn(yarnType: YarnType) = viewModelScope.launch {
        yarnStashRepository.deleteStashedYarn(yarnType)
    }
}