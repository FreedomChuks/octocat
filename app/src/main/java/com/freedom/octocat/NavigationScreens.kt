package com.freedom.octocat

import androidx.navigation3.runtime.NavKey
import com.freedom.model.CatModel
import kotlinx.serialization.Serializable

interface NavigationScreens:NavKey {
    @Serializable data object BreedList:NavigationScreens
    @Serializable class BreedDetails(val catModel: CatModel):NavigationScreens
}