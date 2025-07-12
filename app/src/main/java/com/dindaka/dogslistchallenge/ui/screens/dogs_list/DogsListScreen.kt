package com.dindaka.dogslistchallenge.ui.screens.dogs_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.dindaka.dogslistchallenge.R
import com.dindaka.dogslistchallenge.data.model.DogData
import com.dindaka.dogslistchallenge.ui.screens.dogs_list.state.UiState
import com.dindaka.dogslistchallenge.ui.screens.dogs_list.viewmodel.DogsListViewModel
import com.dindaka.dogslistchallenge.ui.theme.TextPrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun DogsListScreen(viewModel: DogsListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.title_dogs_we_love),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = TextPrimaryColor
                )
            )
        },
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            when (uiState) {
                UiState.Loading -> LoadingComponent(modifier)
                UiState.Empty -> EmptyComponent(modifier){
                    viewModel.loadData(true)
                }
                is UiState.Error -> ErrorComponent(modifier, (uiState as UiState.Error).message){
                    viewModel.loadData(true)
                }
                is UiState.Success -> ListComponent(modifier, (uiState as UiState.Success).data)
            }
        }
    )
}

@Composable
fun ListComponent(modifier: Modifier, data: List<DogData>) {
    LazyColumn(modifier.fillMaxSize()) {
        items(data) {
            ItemComponent(it)
        }
    }
}

@Composable
fun ItemComponent(item: DogData) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 15.dp)
            .padding(vertical = 15.dp)
    ) {
        Box(
            Modifier
                .weight(1f)
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        bottomStart = 12.dp,
                        topEnd = 12.dp,
                        bottomEnd = 0.dp,
                    )
                )
                .background(Color.White)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.image)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))

            )
        }
        Column(
            Modifier
                .weight(2f)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 0.dp,
                        topEnd = 12.dp,
                        bottomEnd = 12.dp,
                    )
                )
                .background(Color.White)
                .align(Alignment.Bottom)
                .height(180.dp)
                .padding(start = 25.dp)
        ) {
            Text(
                item.name,
                modifier = Modifier.padding(vertical = 15.dp),
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                item.description,
                Modifier
                    .weight(1f)
                    .padding(end = 15.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                stringResource(R.string.lbl_almost_years, item.age),
                modifier = Modifier.padding(bottom = 35.dp, top = 10.dp),
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ErrorComponent(modifier: Modifier, message: String?, fetchData: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message ?: stringResource(R.string.lbl_ups_try_again_d),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {fetchData()}) {
            Text(stringResource(R.string.lbl_fetch_the_dogs_again), color = Color.White)
        }
    }
}

@Composable
fun EmptyComponent(modifier: Modifier, fetchData: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.lbl_empty_list),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {fetchData()}) {
            Text(stringResource(R.string.lbl_fetch_the_dogs_again), color = Color.White)
        }
    }
}

@Composable
fun LoadingComponent(modifier: Modifier) {
    Box(modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}
