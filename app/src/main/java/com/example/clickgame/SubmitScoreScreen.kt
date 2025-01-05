package com.example.clickgame

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.snapshots.SnapshotStateList
@Composable
fun SubmitScoreScreen(
    score: Int,
    leaderboard: SnapshotStateList<Pair<String, Int>>,
    onSave: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Your Score: $score")
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (name.isNotBlank()) {
                // Sauvegarder dans Firestore
                FirebaseUtils.saveScore(name, score,
                    onSuccess = {
                        leaderboard.add(name to score)
                        onSave()
                    },
                    onError = { error ->
                        errorMessage = "Failed to save score: ${error.message}"
                    }
                )
            }
        }) {
            Text("Save")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it)
        }
    }
}
