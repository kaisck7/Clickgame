package com.example.clickgame

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class PlayerScore(val name: String, val score: Int)

object FirebaseUtils {
    private val db = FirebaseFirestore.getInstance()

    // Enregistrer un score dans Firestore
    fun saveScore(name: String, score: Int, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val scoreData = hashMapOf(
            "name" to name,
            "score" to score,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("scores")
            .add(scoreData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e) }
    }

    // Récupérer les scores depuis Firestore
    suspend fun getScores(): List<PlayerScore> {
        return try {
            val result = db.collection("scores")
                .orderBy("score", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()

            result.documents.mapNotNull { doc ->
                val name = doc.getString("name")
                val score = doc.getLong("score")?.toInt()
                if (name != null && score != null) PlayerScore(name, score) else null
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
