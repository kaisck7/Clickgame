package com.example.clickgame

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Appliquer le thème principal après le splash screen
        setTheme(R.style.Theme_ClickGame)

        super.onCreate(savedInstanceState)

        // Test Firebase connection (peut être retiré une fois la configuration vérifiée)
        FirebaseFirestore.getInstance().collection("test")
            .get()
            .addOnSuccessListener {
                Log.d("FirebaseTest", "Connection successful: ${it.documents}")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseTest", "Connection failed: ${e.message}")
            }

        // Définir le contenu avec Jetpack Compose
        setContent {
            val leaderboard = mutableStateListOf<Pair<String, Int>>() // Mutable state list for leaderboard
            MyAppNavigation(leaderboard = leaderboard)
        }
    }
}
