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
./gradlew :androidApp:assembleDebug
```

Build release APK:
```bash
./gradlew :androidApp:assembleRelease
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
./gradlew :composeApp:testAndroidHostTest  # Android
./gradlew :composeApp:iosSimulatorArm64Test  # iOS simulator
```

Run a single test class:
```bash
./gradlew :composeApp:testAndroidHostTest --tests "de.niilz.kmp.workoutlog.SomeTest"
```

## Architecture

### Module Structure

- **`:composeApp`** — KMP library module (uses `com.android.kotlin.multiplatform.library`). Contains all shared code, UI, and platform expect/actual implementations.
- **`:androidApp`** — Android application module (uses `com.android.application`). Contains `MainActivity`, `AndroidManifest.xml`, and Android resources. Depends on `:composeApp`.
- **`iosApp/`** — Xcode project that consumes the iOS framework built from `:composeApp`.

### Multiplatform Structure

The shared codebase follows the standard Kotlin Multiplatform structure:

- **commonMain** (`composeApp/src/commonMain/kotlin`): Shared code for all platforms. This includes the main App composable, business logic, and UI that works across platforms.
- **androidMain** (`composeApp/src/androidMain/kotlin`): Android-specific expect/actual implementations (e.g., `Platform.android.kt`).
- **iosMain** (`composeApp/src/iosMain/kotlin`): iOS-specific implementations, including the MainViewController and iOS platform implementation.

### Expect/Actual Pattern

Platform-specific code uses Kotlin's expect/actual mechanism. See `Platform.kt` (interface in commonMain) with actual implementations in `Platform.android.kt` and `Platform.ios.kt`.

When adding platform-specific functionality:
1. Define the `expect` declaration in commonMain
2. Provide `actual` implementations in androidMain and iosMain

### Entry Points

- **Android**: `androidApp/.../MainActivity.kt` - Uses `setContent { App() }` to launch the Compose UI
- **iOS**: `MainViewController.kt` - Returns a `ComposeUIViewController` wrapping the shared App composable
- **Shared UI**: `App.kt` - The main Compose entry point used by both platforms

## Development Notes

- Target Android SDK: 36 (minimum SDK 24)
- Kotlin version: 2.3.0
- AGP: 9.0.1
- Compose Multiplatform version: 1.10.0
- JVM target: Java 11
- iOS targets: `iosArm64` and `iosSimulatorArm64` only (no x86 simulator support)
- iOS framework is built as a static framework (see `composeApp/build.gradle.kts`)
- All dependency versions are managed in `gradle/libs.versions.toml`
- Two-module project: `:androidApp` (app shell) + `:composeApp` (KMP shared library)
