package de.niilz.kmp.workoutlog

import de.niilz.kmp.workoutlog.data.defaultWorkouts
import de.niilz.kmp.workoutlog.model.Exercise
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class WorkoutViewModelTest {

    // --- Workout CRUD ---

    @Test
    fun addWorkout() {
        val vm = WorkoutViewModel()
        val initialSize = vm.workouts.value.size

        vm.addWorkout("New Workout")

        val workouts = vm.workouts.value
        assertEquals(initialSize + 1, workouts.size)
        assertTrue(workouts.any { it.name == "New Workout" })
    }

    @Test
    fun getWorkoutById() {
        val vm = WorkoutViewModel()

        val found = vm.getWorkoutById("1")
        assertNotNull(found)
        assertEquals("Upper Body Strength", found.name)

        val notFound = vm.getWorkoutById("nonexistent")
        assertNull(notFound)
    }

    @Test
    fun updateWorkout() {
        val vm = WorkoutViewModel()

        vm.updateWorkout("1", "Renamed Workout")

        val updated = vm.getWorkoutById("1")
        assertNotNull(updated)
        assertEquals("Renamed Workout", updated.name)

        // Other workouts remain untouched
        val other = vm.getWorkoutById("2")
        assertNotNull(other)
        assertEquals("Lower Body Power", other.name)
    }

    @Test
    fun deleteWorkout() {
        val vm = WorkoutViewModel()
        val initialSize = vm.workouts.value.size

        vm.deleteWorkout("1")

        val workouts = vm.workouts.value
        assertEquals(initialSize - 1, workouts.size)
        assertNull(vm.getWorkoutById("1"))
    }

    // --- Exercise CRUD ---

    @Test
    fun addExercise() {
        val vm = WorkoutViewModel()
        val exercise = Exercise(id = "tmp", name = "Bicep Curls", sets = 3, reps = 12, weight = 15f)

        vm.addExercise("1", exercise)

        val workout = vm.getWorkoutById("1")
        assertNotNull(workout)
        assertTrue(workout.exercises.any { it.name == "Bicep Curls" })
    }

    @Test
    fun addExerciseAssignsNewId() {
        val vm = WorkoutViewModel()
        val originalId = "my-known-id"
        val exercise = Exercise(id = originalId, name = "Tricep Dips", sets = 3, reps = 10, weight = 0f)

        vm.addExercise("1", exercise)

        val workout = vm.getWorkoutById("1")
        assertNotNull(workout)
        val added = workout.exercises.find { it.name == "Tricep Dips" }
        assertNotNull(added)
        assertNotEquals(originalId, added.id)
    }

    @Test
    fun updateExercise() {
        val vm = WorkoutViewModel()
        val updated = Exercise(id = "1-1", name = "Bench Press", sets = 5, reps = 5, weight = 100f)

        vm.updateExercise("1", updated)

        val workout = vm.getWorkoutById("1")
        assertNotNull(workout)
        val exercise = workout.exercises.find { it.id == "1-1" }
        assertNotNull(exercise)
        assertEquals(5, exercise.sets)
        assertEquals(5, exercise.reps)
        assertEquals(100f, exercise.weight)

        // Other exercises untouched
        val other = workout.exercises.find { it.id == "1-2" }
        assertNotNull(other)
        assertEquals("Overhead Press", other.name)
    }

    @Test
    fun deleteExercise() {
        val vm = WorkoutViewModel()
        val initialCount = vm.getWorkoutById("1")!!.exercises.size

        vm.deleteExercise("1", "1-1")

        val workout = vm.getWorkoutById("1")
        assertNotNull(workout)
        assertEquals(initialCount - 1, workout.exercises.size)
        assertNull(workout.exercises.find { it.id == "1-1" })
        // Other exercises remain
        assertNotNull(workout.exercises.find { it.id == "1-2" })
    }

    // --- Completion & Queries ---

    @Test
    fun toggleExerciseComplete() {
        val vm = WorkoutViewModel()

        // Initially false
        assertFalse(vm.getWorkoutById("1")!!.exercises.first { it.id == "1-1" }.completed)

        vm.toggleExerciseComplete("1", "1-1")
        assertTrue(vm.getWorkoutById("1")!!.exercises.first { it.id == "1-1" }.completed)

        vm.toggleExerciseComplete("1", "1-1")
        assertFalse(vm.getWorkoutById("1")!!.exercises.first { it.id == "1-1" }.completed)
    }

    @Test
    fun toggleExerciseCompleteOnlyAffectsTarget() {
        val vm = WorkoutViewModel()

        vm.toggleExerciseComplete("1", "1-1")

        // Sibling exercise remains unchanged
        val sibling = vm.getWorkoutById("1")!!.exercises.first { it.id == "1-2" }
        assertFalse(sibling.completed)
    }

    @Test
    fun getAllUniqueExercises() {
        val vm = WorkoutViewModel()
        // Add a duplicate exercise name ("Bench Press" already exists in workout "1")
        val duplicate = Exercise(id = "tmp", name = "Bench Press", sets = 2, reps = 6, weight = 60f)
        vm.addExercise("2", duplicate)

        val unique = vm.getAllUniqueExercises()
        val benchPressCount = unique.count { it.name == "Bench Press" }
        assertEquals(1, benchPressCount)

        // Total unique names should equal all distinct exercise names across all workouts
        val allNames = vm.workouts.value.flatMap { it.exercises }.map { it.name }.distinct()
        assertEquals(allNames.size, unique.size)
    }
}
