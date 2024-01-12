package com.crocheteer.crocheteer.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.crocheteer.crocheteer.data.YarnStashRepository
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddYarnViewModel @Inject constructor(
    private val yarnStashRepository: YarnStashRepository
) : ViewModel() {
    suspend fun getCompanyNames(searchTerm: String): List<String> =
        yarnStashRepository.getCompanyName(searchTerm)

    suspend fun stashYarn(yarn: YarnTypeWithColors) {
        yarnStashRepository.addStashedYarn(yarn)
    }
}