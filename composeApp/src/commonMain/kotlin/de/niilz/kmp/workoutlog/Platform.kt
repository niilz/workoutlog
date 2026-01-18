package de.niilz.kmp.workoutlog

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform