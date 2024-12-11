package com.example.clickgame

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.snapshots.SnapshotStateList

@Composable
fun MyAppNavigation(leaderboard: SnapshotStateList<Pair<String, Int>>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "game") {
        composable("game") {
            GameScreen(onGameComplete = { score ->
                navController.navigate("submitScore/$score")
            })
        }
        composable("submitScore/{score}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
            SubmitScoreScreen(score = score, leaderboard = leaderboard) {
                navController.navigate("leaderboard")
            }
        }
        composable("leaderboard") {
            LeaderboardScreen(leaderboard = leaderboard)
        }
    }
}
