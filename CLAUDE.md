# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Kotlin Multiplatform (KMP) project targeting Android and iOS using Compose Multiplatform for shared UI code. It's a workout tracking app in early stages (scaffolding complete, features not yet implemented). See `TODO.md` for the phased project roadmap.

Package namespace: `de.niilz.kmp.workoutlog`

## Build System

The project uses Gradle with Kotlin DSL. All version dependencies are centralized in `gradle/libs.versions.toml`.

### Android Commands

Build debug APK:
```bash
./gradlew :composeApp:assembleDebug
```

Build release APK:
```bash
./gradlew :composeApp:assembleRelease
```

### iOS Commands

The iOS app must be built through Xcode. Open the `iosApp` directory in Xcode or use the IDE's run configuration.

### Testing

Run all tests:
```bash
./gradlew test
```

Run tests for a specific platform:
```bash
./gradlew :composeApp:testDebugUnitTest  # Android
./gradlew :composeApp:iosSimulatorArm64Test  # iOS simulator
```

Run a single test class:
```bash
./gradlew :composeApp:testDebugUnitTest --tests "de.niilz.kmp.workoutlog.SomeTest"
```

## Architecture

### Multiplatform Structure

The codebase follows the standard Kotlin Multiplatform structure with three main source set categories:

- **commonMain** (`composeApp/src/commonMain/kotlin`): Shared code for all platforms. This includes the main App composable, business logic, and UI that works across platforms.
- **androidMain** (`composeApp/src/androidMain/kotlin`): Android-specific implementations, including MainActivity and the Android platform implementation.
- **iosMain** (`composeApp/src/iosMain/kotlin`): iOS-specific implementations, including the MainViewController and iOS platform implementation.

### Expect/Actual Pattern

Platform-specific code uses Kotlin's expect/actual mechanism. See `Platform.kt` (interface in commonMain) with actual implementations in `Platform.android.kt` and `Platform.ios.kt`.

When adding platform-specific functionality:
1. Define the `expect` declaration in commonMain
2. Provide `actual` implementations in androidMain and iosMain

### Entry Points

- **Android**: `MainActivity.kt` - Uses `setContent { App() }` to launch the Compose UI
- **iOS**: `MainViewController.kt` - Returns a `ComposeUIViewController` wrapping the shared App composable
- **Shared UI**: `App.kt` - The main Compose entry point used by both platforms

## Development Notes

- Target Android SDK: 36 (minimum SDK 24)
- Kotlin version: 2.3.0
- AGP: 9.0.0
- Compose Multiplatform version: 1.10.0
- JVM target: Java 11
- iOS targets: `iosArm64` and `iosSimulatorArm64` only (no x86 simulator support)
- iOS framework is built as a static framework (see `composeApp/build.gradle.kts:24`)
- All dependency versions are managed in `gradle/libs.versions.toml`
- Single module project (`:composeApp`) — no separate shared/domain modules yet
