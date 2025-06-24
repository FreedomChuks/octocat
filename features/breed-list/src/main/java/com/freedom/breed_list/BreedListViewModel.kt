package com.freedom.breed_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.freedom.data.BreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel @Inject constructor(
    repository: BreedRepository
): ViewModel(){
    val cats = repository.getCatBreed(limit = PAGE_SIZE).cachedIn(viewModelScope)

    companion object {
        private const val PAGE_SIZE = 10
    }
}

