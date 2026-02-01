package de.niilz.kmp.workoutlog.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import de.niilz.kmp.workoutlog.model.Workout

@Composable
fun DeleteWorkoutDialog(
    workout: Workout,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Workout") },
        text = {
            Text("Delete \"${workout.name}\" with ${workout.exercises.size} exercise(s)?")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    )
}
