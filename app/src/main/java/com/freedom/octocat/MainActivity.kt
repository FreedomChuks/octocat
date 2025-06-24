package com.freedom.octocat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.freedom.breed_details.BreedDetailScreen
import com.freedom.breed_list.BreedListScreen
import com.freedom.designsystem.theme.OctocatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack = rememberNavBackStack<NavigationScreens>(NavigationScreens.BreedList)
            OctocatTheme  {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        entry<NavigationScreens.BreedList> {
                            BreedListScreen(
                                onCardClicked = {
                                    backStack.add(NavigationScreens.BreedDetails(it))
                                }
                            )
                        }
                        entry<NavigationScreens.BreedDetails> { key->
                            BreedDetailScreen(
                                onBack = { backStack.removeLastOrNull()},
                                catModel = key.catModel
                            )
                        }
                    }
                )
            }
        }
    }

}
