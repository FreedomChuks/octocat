package com.freedom.breed_list


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.freedom.breed_list.component.BreedCard
import com.freedom.model.CatModel

@Composable
fun BreedListScreen(
    viewModel: BreedListViewModel = hiltViewModel(),
    onCardClicked: (CatModel) -> Unit
) {
    val breeds = viewModel.cats.collectAsLazyPagingItems()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(containerColor = MaterialTheme.colorScheme.surface) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = viewModel::onSearchQueryChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search for a breed") },
                singleLine = true
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(breeds.itemCount) { index ->
                    breeds[index]?.let {
                        BreedCard(
                            catModel = it,
                            onCardClick = onCardClicked
                        )
                    }
                }

                when (breeds.loadState.append) {
                    is LoadState.Loading -> item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is LoadState.Error -> item {
                        Text(
                            text = "Error loading more",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { breeds.retry() }
                                .padding(16.dp),
                            color = Color.Red
                        )
                    }

                    else -> item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}


