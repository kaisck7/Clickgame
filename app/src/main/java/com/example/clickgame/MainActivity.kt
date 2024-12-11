package com.example.clickgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val leaderboard = mutableStateListOf<Pair<String, Int>>() // Mutable state list for leaderboard
            MyAppNavigation(leaderboard = leaderboard) // Pass the leaderboard as a parameter
        }
    }
}
