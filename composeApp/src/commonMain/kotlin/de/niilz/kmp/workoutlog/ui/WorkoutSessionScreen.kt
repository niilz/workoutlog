package de.niilz.kmp.workoutlog.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import de.niilz.kmp.workoutlog.WorkoutViewModel
import de.niilz.kmp.workoutlog.model.Exercise
import de.niilz.kmp.workoutlog.model.Workout
import de.niilz.kmp.workoutlog.ui.dialogs.AddExerciseDialog
import de.niilz.kmp.workoutlog.ui.dialogs.DeleteExerciseDialog
import de.niilz.kmp.workoutlog.ui.dialogs.EditExerciseDialog
import de.niilz.kmp.workoutlog.ui.theme.Green100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutSessionScreen(
    workout: Workout?,
    viewModel: WorkoutViewModel,
    onBack: () -> Unit,
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var editingExercise by remember { mutableStateOf<Exercise?>(null) }
    var deletingExercise by remember { mutableStateOf<Exercise?>(null) }

    if (workout == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("Workout not found.")
        }
        return
    }

    val completedCount = workout.exercises.count { it.completed }
    val totalCount = workout.exercises.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
    val allComplete = totalCount > 0 && completedCount == totalCount

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(workout.name) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                )
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Exercise")
            }
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (allComplete) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            "Workout Complete!",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        )
                    }
                }
            }

            items(workout.exercises, key = { it.id }) { exercise ->
                ExerciseCard(
                    exercise = exercise,
                    onToggle = { viewModel.toggleExerciseComplete(workout.id, exercise.id) },
                    onEdit = { editingExercise = exercise },
                    onDelete = { deletingExercise = exercise },
                )
            }

            if (workout.exercises.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            "No exercises yet. Tap + to add one!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddExerciseDialog(
            libraryExercises = viewModel.getAllUniqueExercises(),
            onDismiss = { showAddDialog = false },
            onConfirm = { exercise ->
                viewModel.addExercise(workout.id, exercise)
                showAddDialog = false
            },
        )
    }

    editingExercise?.let { exercise ->
        EditExerciseDialog(
            exercise = exercise,
            onDismiss = { editingExercise = null },
            onConfirm = { updated ->
                viewModel.updateExercise(workout.id, updated)
                editingExercise = null
            },
        )
    }

    deletingExercise?.let { exercise ->
        DeleteExerciseDialog(
            exercise = exercise,
            onDismiss = { deletingExercise = null },
            onConfirm = {
                viewModel.deleteExercise(workout.id, exercise.id)
                deletingExercise = null
            },
        )
    }
}

@Composable
private fun ExerciseCard(
    exercise: Exercise,
    onToggle: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (exercise.completed) Green100 else MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Checkbox(
                checked = exercise.completed,
                onCheckedChange = { onToggle() },
            )
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(
                    exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (exercise.completed) TextDecoration.LineThrough else TextDecoration.None,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("${exercise.sets} sets", style = MaterialTheme.typography.bodySmall)
                    Text("${exercise.reps} reps", style = MaterialTheme.typography.bodySmall)
                    Text(
                        if (exercise.weight == 0f) "Bodyweight" else "${exercise.weight} kg",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                if (exercise.description.isNotBlank()) {
                    Text(
                        exercise.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
