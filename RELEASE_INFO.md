# Tunevol Releases

This document tracks all available releases for Tunevol. 

## [v1.5.0] - 2026-10-19 (Latest)
[Download on GitHub](https://github.com/NETO-DEV66/Flow-tune-/releases/tag/v1.5.0)

**Renamed to Flow Tune**

- The app is now called **Flow Tune** everywhere: the launcher label, the in-app text, the website, the release assets and the Android application ID (`com.flowtune.app`).
- The rename is a clean break for the Android package name, so **Flow Tune installs alongside an older Flow Music build** instead of upgrading it. Your playlists, downloads and settings stay with the old install; the new app starts fresh.
- Package namespace, Kotlin sources, JNI native symbols, ProGuard rules, the Room schema directory and every URL were updated together.

> [!IMPORTANT]
> Because the application ID changed, this is a side-by-side install, not an in-place update. An installed Flow Music build will **not** receive this as an automatic update.

## [v1.4.5] - 2026-10-19
[Download on GitHub](https://github.com/NETO-DEV66/Flow-tune-/releases/tag/v1.4.5)

**New Icon and Branding**
- The app icon is new: a soundwave mark in the brand violet-to-cyan gradient on a deep near-black tile. It replaces the red mark inherited from the upstream project.
- Every launcher variant was regenerated — standard, round, adaptive foreground, monochrome (themed) and the Android TV banner.
- The in-app logo changed too. It appears on the About screen, the welcome dialog, the player artwork fallback, the playback notification and the update notification.
- The website logo and favicon now use the same mark.

**Fixes**
- The update check compared versions for inequality instead of order, so a build newer than the latest release was offered a downgrade. It now compares versions in order.
- A download notification referenced an icon that did not exist, which broke the build.

## [v1.4.4] - 2026-10-19
[Download on GitHub](https://github.com/NETO-DEV66/Flow-tune-/releases/tag/v1.4.4)

**Identity**
- The project now lives at [NETO-DEV66/Flow-tune-](https://github.com/NETO-DEV66/Flow-tune-); all in-app links, update checks and repository references were repointed to it.

**Support**
- Donations are now a single UPI ID: `mrsameer1906@okhdfcbank`, available in the app (About and the welcome dialog), the README and the website. The previous Buy Me a Coffee, Patreon and cryptocurrency options were removed.

**Fixes**
- In-app update downloads pointed at a file name that does not exist, so the install step could not complete. It now resolves the real release asset.
- The website's download panel showed a stale version, size and checksum because the release event did not trigger a redeploy. It now refreshes whenever a release finishes.

**Other Changes**
- Removed unused social and donation assets.

## [v1.2.2] - 2026-08-28
[Download on GitHub](https://github.com/NETO-DEV66/Flow-tune-/releases/tag/v1.2.2)

**Bug Fixes**
- Fixed a crash that occurred when adding a song to a playlist, album, or artist before it was fully loaded.
- Fixed a crash caused by outdated saved settings after an app update; the app now falls back to a safe default instead of crashing.
- Fixed Spotify login issues where signing in with Google, Apple, or Facebook could show a black screen or fail to complete.
- Fixed the "Update Available" dialog not matching the app's overall theme and styling.

**Design Improvements**
- Updated input fields and dialog buttons (including in Spotify Import) to use a more rounded, modern look consistent with Material You design.

**Other Changes**
- Updated select app components to their latest stable versions for improved reliability.

## [v1.2.1] - 2026-08-28
[Download on GitHub](https://github.com/NETO-DEV66/Flow-tune-/releases/tag/v1.2.1)

I am pleased to announce the initial release of the updated Flow Tune repository.

Recently, the project was subjected to a legal takedown notice. Since then, I have taken all necessary actions and made the required adjustments to the codebase and documentation to ensure full legal compliance. 

With these changes complete, I am excited to restore access to the project. I would like to extend my deepest gratitude to all of you for your unwavering support and patience during this period. Thank you for standing by me.

---

## Pull Request & Release Note Guidelines

**ATTENTION CONTRIBUTORS:** To maintain a clean and standardized changelog, all community contributions added to this file MUST strictly follow this format:

`- \`<type>(<scope>): <summary>\` ([#PR_NUMBER](URL)) by @username`

- **PR Titles** must follow [Conventional Commits](https://www.conventionalcommits.org/).
- **Descriptions** must be clear, concise, and professional.
- PRs that do not follow this strict formatting will **not** be merged.
