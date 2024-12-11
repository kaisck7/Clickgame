package com.example.clickgame

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LeaderboardScreen(leaderboard: SnapshotStateList<Pair<String, Int>>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Leaderboard", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(leaderboard.sortedByDescending { it.second }.size) { index ->
                val (name, score) = leaderboard.sortedByDescending { it.second }[index]
                Text("${index + 1}. $name: $score")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
