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
    // Génération initiale d'une liste de 5 nombres aléatoires
    var numbers by remember { mutableStateOf(List(5) { Random.nextInt(1000, 9999) }) } // Non triée

    // Liste des numéros cliqués par l'utilisateur
    var clickedButtons by remember { mutableStateOf(mutableListOf<Int>()) }

    // Temps restant pour terminer la partie (25 secondes au départ)
    var timeLeft by remember { mutableStateOf(25) }

    // Message d'erreur affiché en cas d'échec
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Effet lancé pour gérer le compte à rebours
    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            kotlinx.coroutines.delay(1000) // Réduire le temps toutes les secondes
            timeLeft--
        }
        // Vérifier si le temps est écoulé sans sélection complète
        if (timeLeft == 0 && clickedButtons.size < 5) {
            errorMessage = "Time's up! You lost."
        }
    }

    // Disposition principale de l'écran
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affichage du temps restant
        Text("Time left: $timeLeft seconds")
        Spacer(modifier = Modifier.height(16.dp))

        // Affichage des boutons pour les numéros générés
        numbers.forEach { number ->
            Button(
                onClick = {
                    // Ajouter ou retirer un numéro cliqué de la liste
                    if (number in clickedButtons) {
                        clickedButtons.remove(number)
                    } else if (clickedButtons.size < 5) {
                        clickedButtons.add(number)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                // Indiquer si le bouton a été sélectionné
                Text(if (number in clickedButtons) "✔ $number" else "$number")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Afficher le bouton "Finish" si 5 numéros sont sélectionnés
        if (clickedButtons.size == 5) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (clickedButtons == clickedButtons.sortedDescending()) {
                    val finalScore = 100 - (25 - timeLeft) * 4 // Calcul du score final
                    onGameComplete(finalScore) // Naviguer vers l'écran de soumission
                } else {
                    errorMessage = "Incorrect order! Try again."
                    clickedButtons.clear()
                }
            }) {
                Text("Finish")
            }
        }

        // Afficher un message d'erreur et un bouton pour réessayer
        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it) // Afficher le message d'erreur
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                // Réinitialiser les données du jeu
                errorMessage = null
                clickedButtons.clear()
                numbers = List(5) { Random.nextInt(1000, 9999) } // Générer de nouveaux numéros
                timeLeft = 25 // Réinitialiser le temps
            }) {
                Text("Retry")
            }
        }
    }
}
