package com.dindaka.dogslistchallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dindaka.dogslistchallenge.ui.screens.dogs_list.DogsListScreen
import com.dindaka.dogslistchallenge.ui.theme.DogsListChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogsListChallengeTheme {
                DogsListScreen()
            }
        }
    }
}