package com.example.clickgame

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LeaderboardScreen(leaderboard: SnapshotStateList<Pair<String, Int>>) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val db = FirebaseFirestore.getInstance()
            val scores = FirebaseUtils.getScores() // Supposons que cette fonction récupère les scores
            leaderboard.clear()
            leaderboard.addAll(scores.map { it.name to it.score })
            isLoading = false
        } catch (e: Exception) {
            println("Error loading scores: $e")
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Leaderboard", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Text("Loading...")
        } else {
            LazyColumn {
                items(leaderboard.sortedByDescending { it.second }.size) { index ->
                    val (name, score) = leaderboard.sortedByDescending { it.second }[index]
                    Text("${index + 1}. $name: $score")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
