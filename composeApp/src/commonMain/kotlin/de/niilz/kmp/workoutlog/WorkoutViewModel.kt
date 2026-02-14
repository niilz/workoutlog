package de.niilz.kmp.workoutlog

import androidx.lifecycle.ViewModel
import de.niilz.kmp.workoutlog.data.defaultWorkouts
import de.niilz.kmp.workoutlog.model.Exercise
import de.niilz.kmp.workoutlog.model.Workout
import de.niilz.kmp.workoutlog.model.generateId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WorkoutViewModel : ViewModel() {
    private val _workouts = MutableStateFlow(defaultWorkouts)
    val workouts: StateFlow<List<Workout>> = _workouts.asStateFlow()

    fun getWorkoutById(id: String): Workout? =
        _workouts.value.find { it.id == id }

    fun addWorkout(name: String) {
        val workout = Workout(id = generateId(), name = name)
        _workouts.update { it + workout }
    }

    fun updateWorkout(id: String, name: String) {
        _workouts.update { workouts ->
            workouts.map { if (it.id == id) it.copy(name = name) else it }
        }
    }

    fun deleteWorkout(id: String) {
        _workouts.update { workouts ->
            workouts.filter { it.id != id }
        }
    }

    fun addExercise(workoutId: String, exercise: Exercise) {
        _workouts.update { workouts ->
            workouts.map { workout ->
                if (workout.id == workoutId) {
                    workout.copy(exercises = workout.exercises + exercise.copy(id = generateId()))
                } else workout
            }
        }
    }

    fun updateExercise(workoutId: String, exercise: Exercise) {
        _workouts.update { workouts ->
            workouts.map { workout ->
                if (workout.id == workoutId) {
                    workout.copy(
                        exercises = workout.exercises.map {
                            if (it.id == exercise.id) exercise else it
                        }
                    )
                } else workout
            }
        }
    }

    fun deleteExercise(workoutId: String, exerciseId: String) {
        _workouts.update { workouts ->
            workouts.map { workout ->
                if (workout.id == workoutId) {
                    workout.copy(exercises = workout.exercises.filter { it.id != exerciseId })
                } else workout
            }
        }
    }

    fun toggleExerciseComplete(workoutId: String, exerciseId: String) {
        _workouts.update { workouts ->
            workouts.map { workout ->
                if (workout.id == workoutId) {
                    workout.copy(
                        exercises = workout.exercises.map {
                            if (it.id == exerciseId) it.copy(completed = !it.completed) else it
                        }
                    )
                } else workout
            }
        }
    }

    fun getAllUniqueExercises(): List<Exercise> {
        return _workouts.value
            .flatMap { it.exercises }
            .distinctBy { it.name }
    }
}
