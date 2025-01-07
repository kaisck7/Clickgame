package com.example.clickgame

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameScreen(onGameComplete: (Int) -> Unit) {
    var numbers by remember { mutableStateOf(List(5) { Random.nextInt(1000, 9999) }) }
    var clickedButtons by remember { mutableStateOf(mutableListOf<Int>()) }
    var timeLeft by remember { mutableStateOf(25) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000) // Proper use of delay in a coroutine
            timeLeft--
        }
        if (timeLeft == 0 && clickedButtons.size < 5) {
            errorMessage = "Time's up! You lost."
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
                if (clickedButtons == clickedButtons.sortedDescending()) {
                    val finalScore = 100 - (25 - timeLeft) * 4
                    onGameComplete(finalScore)
                } else {
                    errorMessage = "Incorrect order! Try again."
                    clickedButtons.clear()
                }
            }) {
                Text("Finish")
            }
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                errorMessage = null
                clickedButtons.clear()
                numbers = List(5) { Random.nextInt(1000, 9999) }
                timeLeft = 25
            }) {
                Text("Retry")
            }
        }
    }
}
