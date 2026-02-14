package de.niilz.kmp.workoutlog.data

import de.niilz.kmp.workoutlog.model.Exercise
import de.niilz.kmp.workoutlog.model.Workout

val defaultWorkouts = listOf(
    Workout(
        id = "1",
        name = "Upper Body Strength",
        exercises = listOf(
            Exercise(id = "1-1", name = "Bench Press", sets = 4, reps = 8, weight = 80f, description = "Flat barbell bench press"),
            Exercise(id = "1-2", name = "Overhead Press", sets = 3, reps = 10, weight = 40f, description = "Standing barbell press"),
            Exercise(id = "1-3", name = "Pull-ups", sets = 3, reps = 10, weight = 0f, description = "Wide grip pull-ups"),
            Exercise(id = "1-4", name = "Dumbbell Rows", sets = 3, reps = 12, weight = 30f, description = "Single arm dumbbell rows"),
        ),
    ),
    Workout(
        id = "2",
        name = "Lower Body Power",
        exercises = listOf(
            Exercise(id = "2-1", name = "Squats", sets = 5, reps = 5, weight = 100f, description = "Back squats"),
            Exercise(id = "2-2", name = "Deadlift", sets = 3, reps = 8, weight = 120f, description = "Conventional deadlift"),
            Exercise(id = "2-3", name = "Lunges", sets = 3, reps = 12, weight = 20f, description = "Walking lunges with dumbbells"),
            Exercise(id = "2-4", name = "Calf Raises", sets = 4, reps = 15, weight = 60f, description = "Standing calf raises"),
        ),
    ),
    Workout(
        id = "3",
        name = "Full Body HIIT",
        exercises = listOf(
            Exercise(id = "3-1", name = "Burpees", sets = 3, reps = 15, weight = 0f, description = "Full burpees with push-up"),
            Exercise(id = "3-2", name = "Kettlebell Swings", sets = 3, reps = 20, weight = 24f, description = "Russian kettlebell swings"),
            Exercise(id = "3-3", name = "Box Jumps", sets = 3, reps = 12, weight = 0f, description = "24-inch box jumps"),
        ),
    ),
    Workout(
        id = "4",
        name = "Core & Flexibility",
        exercises = listOf(
            Exercise(id = "4-1", name = "Plank", sets = 3, reps = 1, weight = 0f, description = "60 second hold"),
            Exercise(id = "4-2", name = "Russian Twists", sets = 3, reps = 20, weight = 10f, description = "With medicine ball"),
            Exercise(id = "4-3", name = "Leg Raises", sets = 3, reps = 15, weight = 0f, description = "Hanging leg raises"),
        ),
    ),
)
