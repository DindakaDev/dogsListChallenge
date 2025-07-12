package com.dindaka.dogslistchallenge.ui.screens.dogs_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dindaka.dogslistchallenge.data.model.DogData
import com.dindaka.dogslistchallenge.ui.screens.dogs_list.state.UiState
import com.dindaka.dogslistchallenge.ui.screens.dogs_list.viewmodel.DogsListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsListScreen(viewModel: DogsListViewModel = hiltViewModel()){
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Dogs We Love") }
            )
        },
        content = {innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            when(uiState){
                UiState.Loading -> LoadingComponent(modifier)
                UiState.Empty -> EmptyComponent(modifier)
                is UiState.Error -> ErrorComponent(modifier, (uiState as UiState.Error).exception)
                is UiState.Success -> ListComponent(modifier,(uiState as UiState.Success).data)
            }
        }
    )
}

@Composable
fun ListComponent(modifier: Modifier, data: List<DogData>) {
    LazyColumn(modifier.fillMaxSize()){
        items(data){
            Row{
              Text(it.name)
            }
        }
    }
}

@Composable
fun ErrorComponent(modifier: Modifier, exception: Throwable?) {
    Column(modifier.fillMaxSize()){
        Text("Lista vacia")
    }
}

@Composable
fun EmptyComponent(modifier: Modifier) {
    Column(modifier.fillMaxSize()){
        Text("Lista vacia")
    }
}

@Composable
fun LoadingComponent(modifier: Modifier) {
    Box(modifier.fillMaxSize()){
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}
