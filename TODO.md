# Workout Tracker - Project Plan

## Phase 1: MVP - Core Functionality

### Exercise Management
- [x] Create Exercise data model (Name, Weight, Reps, Description, Date Added)
- [ ] Implement local database for exercise storage
- [x] Build "Add Exercise" screen/dialog
- [x] Implement exercise CRUD operations

### Main Workout Screen
- [x] Design TODO-like workout list UI
- [x] Display exercises with checkboxes
- [x] Implement check-off functionality
- [ ] Persist completion state
- [x] Add visual feedback for completed exercises

### Basic UI/UX
- [x] Set up navigation structure
- [x] Design consistent UI theme
- [x] Implement responsive layouts for both platforms
- [ ] Add basic error handling and user feedback

### Unit Tests
#### WorkoutViewModel - Workout CRUD
- [x] `addWorkout` appends a new workout to the list
- [x] `getWorkoutById` returns the correct workout (and null for unknown id)
- [x] `updateWorkout` renames the correct workout without affecting others
- [x] `deleteWorkout` removes only the targeted workout

#### WorkoutViewModel - Exercise CRUD
- [x] `addExercise` appends an exercise to the correct workout
- [x] `addExercise` assigns a new unique id to the exercise
- [x] `updateExercise` replaces the correct exercise within the correct workout
- [x] `deleteExercise` removes only the targeted exercise from the correct workout

#### WorkoutViewModel - Completion & Queries
- [x] `toggleExerciseComplete` flips completed from false to true and back
- [x] `toggleExerciseComplete` only affects the targeted exercise
- [x] `getAllUniqueExercises` returns distinct exercises by name across all workouts

## Phase 2: Secondary Features

### Statistics Screen
- [ ] Design statistics data model (track exercise history)
- [ ] Build statistics UI with graphs
- [ ] Implement progression visualization (weight/reps over time)
- [ ] Add date range filtering

### Workout Design
- [x] Create Workout data model (collection of exercises)
- [x] Build "Workout Design" screen
- [x] Allow creating/editing/deleting workout plans
- [ ] Implement workout templates (split-training, all-body, etc.)

### Workout Integration
- [ ] Add "Reset Main Screen" functionality
- [ ] Add "Load Workout" functionality
- [ ] Implement workout selection UI
- [ ] Handle conflicts when populating from templates

## Phase 3: Cloud Infrastructure

### Backend Setup
- [ ] Choose backend language (Rust vs Kotlin)
- [ ] Set up REST API server
- [ ] Choose database (PostgreSQL vs MongoDB)
- [ ] Design database schema
- [ ] Implement API endpoints (CRUD for exercises, workouts, user data)
- [ ] Set up containerization (Docker)
- [ ] Choose cloud provider (IONOS vs STACKIT)
- [ ] Deploy backend to cloud

### Authentication & Authorization
- [ ] Design user account system
- [ ] Implement user registration
- [ ] Implement login/logout
- [ ] Add 2FA support
- [ ] Implement passkey support
- [ ] Add session management
- [ ] Implement secure token handling

### Data Synchronization
- [ ] Design sync strategy (conflict resolution)
- [ ] Implement data migration from local to cloud
- [ ] Build sync service in mobile app
- [ ] Add offline-first capabilities
- [ ] Implement sync conflict UI

### Mobile App Integration
- [ ] Add authentication screens
- [ ] Integrate API client
- [ ] Implement data sync on app launch
- [ ] Add account management screen
- [ ] Handle network errors gracefully

## Phase 4: Polish & Refinement

- [ ] Performance optimization
- [ ] Comprehensive testing (unit, integration, E2E)
- [ ] Accessibility improvements
- [ ] Localization support
- [ ] App store preparation (screenshots, descriptions)
- [ ] Privacy policy and terms of service
- [ ] Beta testing program

## Infrastructure Tasks

- [ ] Set up CI/CD pipeline
- [ ] Configure automated testing
- [ ] Set up monitoring and logging
- [ ] Implement backup strategy
- [ ] Security audit
- [ ] GDPR compliance review

## Notes

- Start with device-only implementation (Phase 1-2)
- Cloud features can be added incrementally (Phase 3)
- Each checkbox represents a potential GitHub issue
- Prioritize based on user value and technical dependencies
