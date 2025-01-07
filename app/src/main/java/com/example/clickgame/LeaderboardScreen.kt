package com.example.clickgame

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LeaderboardScreen(leaderboard: SnapshotStateList<Pair<String, Int>>, navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }

    // Chargement des scores depuis Firebase
    LaunchedEffect(Unit) {
        try {
            val scores = FirebaseUtils.getScores()
            leaderboard.clear()
            leaderboard.addAll(scores.map { it.name to it.score })
            isLoading = false
        } catch (e: Exception) {
            println("Error loading scores: ${e.message}")
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre
        Text(
            text = "Leaderboard",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Chargement ou affichage des scores
        if (isLoading) {
            Text("Loading...")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(leaderboard) { (name, score) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = name, style = MaterialTheme.typography.bodyLarge)
                        Text(text = score.toString(), style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour revenir à l'écran d'accueil
        Button(
            onClick = { navController.navigate("game") }, // Navigue vers l'écran d'accueil
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("New Game")
        }
    }
}
