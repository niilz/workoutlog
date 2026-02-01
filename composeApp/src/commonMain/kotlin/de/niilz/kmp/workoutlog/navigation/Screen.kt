package de.niilz.kmp.workoutlog.navigation

sealed class Screen {
    data object WorkoutList : Screen()
    data class WorkoutDetail(val workoutId: String) : Screen()
}
