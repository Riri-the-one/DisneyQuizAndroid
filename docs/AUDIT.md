# Audit and publication record

Date: 2026-11-13. Original: `Projet_Quiz-android`, preserved without source edits. No original Git directory was found. No corresponding repository appeared in the authenticated account's repository listing; repository creation was not attempted with incomplete sources.

The initial OneDrive copy encountered a cloud timeout for `.gitignore`. The remaining 63 relevant files became readable and were copied, including source, XML layouts, assets, Gradle wrapper and build configuration. The source inventory contains 64 relevant files excluding caches/build outputs, personal `local.properties` and Firebase client configuration. The missing ignore file was replaced with a project-appropriate file. Work subsequently continued in an independent temporary directory outside OneDrive because creation of the requested Documents workspace was denied by this session's filesystem permissions.

## Initial implementation

Java Android activity-based quiz; five Disney questions; score/replay/logout screen. Login accepts one hardcoded demonstration email/password, while registration creates a Firebase account. The sign-in placeholder activity has no behavior and no caller. There is no Firestore/Storage application dependency or database rule set to audit here.

## Findings and corrections

- Registered Quiz2, Quiz3 and Score, which were absent from the manifest, and added INTERNET for Firebase.
- Replaced simulated login with Firebase Authentication, validated email/empty password, retained password whitespace, blocked pending submission and provided useful failures. Logout now signs out of Firebase and clears the navigation stack.
- Reused the original five screens through a shared question controller. Answers use resource IDs rather than translated display labels. A submission advances only once; previous screens finish instead of accumulating in the back stack. Score input/output is bounded.
- Made the result progress bar determinate and based its value on the same bounded percentage as the text.
- Added scroll containers and corrected heading text sizes from dp to sp without changing artwork or subject.
- Removed the unused empty sign-in activity/layout; kept the real login screen.
- Added setup documentation, ignored service/personal settings, fictional CI configuration and targeted regressions. No production Firebase action or user data modification was performed.

## Executed validation

- Java scoring harness compiled and ran with the existing JDK 21.0.10: **191 assertions passed**.
- `node --test tests/structure.test.cjs`: **2 tests passed**, checking manifest routes and actual answer mappings.
- All layout XML documents parsed successfully with the Windows XML parser after layout changes.
- `gradlew.bat testDebugUnitTest lintDebug assembleDebug --no-daemon`: failed before project configuration while downloading Gradle 9.2.1 (sandbox network permission denied). These Gradle tests/lint/build did not execute locally.
- Source pattern scan for Google API keys, private-key headers and GitHub token patterns returned no matches in the copied source tree. This is a targeted scan, not proof that arbitrary secrets are impossible.
- No device UI, Firebase login or release signing was tested. No screenshots were fabricated.

## Publication status

Local source is available and prepared. Publication remains blocked by available tooling: the CLI reports failed authentication in a terminal without GitHub network access; the authenticated GitHub connector can update existing repositories but exposes no repository-creation capability. The prepared CI has not run, and no GitHub URL or successful Android build is claimed.
