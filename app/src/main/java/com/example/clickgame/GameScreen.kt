package com.example.clickgame

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun GameScreen(onGameComplete: (Int) -> Unit) {
    var numbers by remember { mutableStateOf(List(5) { Random.nextInt(1000, 9999) }) } // No sorting
    var clickedButtons by remember { mutableStateOf(mutableSetOf<Int>()) }
    var timeLeft by remember { mutableStateOf(25) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            kotlinx.coroutines.delay(1000)
            timeLeft--
        }
        if (timeLeft == 0 || clickedButtons.size == 5) {
            val finalScore = 100 - (25 - timeLeft) * 4 // Example score calculation
            onGameComplete(finalScore)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Time left: $timeLeft seconds")
        Spacer(modifier = Modifier.height(16.dp))

        numbers.forEach { number ->
            Button(
                onClick = {
                    if (number in clickedButtons) {
                        clickedButtons.remove(number)
                    } else if (clickedButtons.size < 5) {
                        clickedButtons.add(number)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (number in clickedButtons) "✔ $number" else "$number")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (clickedButtons.size == 5) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val finalScore = 100 - (25 - timeLeft) * 4
                onGameComplete(finalScore)
            }) {
                Text("Finish")
            }
        }
    }
}