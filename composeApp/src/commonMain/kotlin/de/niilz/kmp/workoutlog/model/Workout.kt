package de.niilz.kmp.workoutlog.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Exercise(
    val id: String,
    val name: String,
    val sets: Int,
    val reps: Int,
    val weight: Float,
    val description: String = "",
    val completed: Boolean = false,
)

data class Workout(
    val id: String,
    val name: String,
    val exercises: List<Exercise> = emptyList(),
)

@OptIn(ExperimentalUuidApi::class)
fun generateId(): String = Uuid.random().toString()
