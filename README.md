# Disney Quiz Android

A small Java Android quiz with five Disney-themed multiple-choice questions, Firebase email/password accounts and a result screen. The interface is in French and retains the original pink/purple artwork and question layouts.

## Features

- Account registration and email/password login through Firebase Authentication.
- Five ordered questions, answer selection validation and a percentage result.
- Replay and sign-out flows, with finished questions removed from the back stack.
- Stable answer IDs, bounded scores and protection against repeated next-button taps.
- Scrollable screens for smaller displays and larger text settings.

## Stack and architecture

Java 11 source compatibility, XML views, AppCompat 1.7.1, Material 1.13.0, AndroidX Activity 1.12.4, ConstraintLayout 2.2.1, CircleImageView 3.1.0 and Firebase BoM 34.11.0 (Auth/Analytics). AGP 9.0.1 and Gradle 9.2.1 run with JDK 21. Compile SDK: 36.1; target SDK: 36; minimum SDK: 31 (Android 12).

`MainActivity` handles login; `Activity_register` handles registration. `Quiz1`–`Quiz5` retain their separate XML screens and declare their question, correct answer ID and destination. `BaseQuizActivity` shares validation/navigation. `QuizScoring` provides Android-independent scoring rules. `Score` displays results, replay and logout. No application database or remote leaderboard is implemented.

## Installation and configuration

1. Install JDK 21 and Android Studio with SDK platform 36.1 and build tools 36.0.0.
2. Open this directory in Android Studio and allow Gradle to resolve dependencies.
3. Use your own Firebase project. Register Android package `com.example.projet_quiz`, enable email/password Authentication and place its downloaded client configuration at `app/google-services.json`. This file is ignored by Git. No service-account credentials are needed.
4. Set the SDK path in `local.properties` if Android Studio has not done so; an example is included.
5. Run `gradlew.bat assembleDebug`, then `gradlew.bat installDebug` with a connected Android 12+ device/emulator. On macOS/Linux use `bash gradlew`.

For compilation without a Firebase account, `python scripts/create-demo-config.py` creates a fictional configuration and refuses to overwrite an existing one. This fixture cannot authenticate users. Real login/registration require your own Firebase configuration and network access. No hardcoded demo password remains.

## Tests and CI

```powershell
.\scripts\test-scoring.ps1
node --test tests/structure.test.cjs
.\gradlew.bat testDebugUnitTest lintDebug assembleDebug --no-daemon
```

The Java harness exercises scoring bounds, five correct answers, wrong answers and percentage calculation. The Node tests verify manifest registration and match each stable answer ID to the actual question text in its layout.

Local results on 13 November 2026: **191 scoring assertions passed; 2 structural tests passed**. All layout XML files were parsed successfully with the Windows XML parser. The Android command stopped at the Gradle distribution download because sandbox networking is restricted; Android unit tests, lint and APK packaging have not been validated. A GitHub Actions workflow is prepared but has not run for this project. No visual emulator/device validation or screenshots are available.

## Limitations and next steps

- Questions are fixed and progress is local to the current run; there is no durable quiz history or leaderboard.
- Firebase authentication requires external setup. The original simulated login and Firebase registration were inconsistent; both now use Firebase, but live account flows still require device validation with fictional test users.
- Display-name updates after registration are best effort. Production account management would need verification/reset-password flows and explicit data lifecycle policies.
- No academic provenance or original artwork license was supplied. Disney characters and images belong to their respective owners; this is not an official Disney application. No new ownership or licensing claim is made. CircleImageView and other libraries retain their upstream licenses.
- Next steps: device tests for rotation, accessibility and offline errors, plus a data-driven question model if the quiz grows.

See [the audit and publication record](docs/AUDIT.md) for the exact validation limits.
