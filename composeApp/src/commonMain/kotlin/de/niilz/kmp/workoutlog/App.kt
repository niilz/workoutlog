package de.niilz.kmp.workoutlog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import de.niilz.kmp.workoutlog.navigation.Screen
import de.niilz.kmp.workoutlog.ui.WorkoutLibraryScreen
import de.niilz.kmp.workoutlog.ui.WorkoutSessionScreen
import de.niilz.kmp.workoutlog.ui.theme.WorkoutLogTheme

@Composable
@Preview
fun App() {
    WorkoutLogTheme {
        val viewModel: WorkoutViewModel = viewModel { WorkoutViewModel() }
        val workouts by viewModel.workouts.collectAsState()
        var currentScreen by remember { mutableStateOf<Screen>(Screen.WorkoutList) }

        when (val screen = currentScreen) {
            is Screen.WorkoutList -> WorkoutLibraryScreen(
                workouts = workouts,
                viewModel = viewModel,
                onWorkoutClick = { id -> currentScreen = Screen.WorkoutDetail(id) },
            )
            is Screen.WorkoutDetail -> WorkoutSessionScreen(
                workout = workouts.find { it.id == screen.workoutId },
                viewModel = viewModel,
                onBack = { currentScreen = Screen.WorkoutList },
            )
        }
    }
}
