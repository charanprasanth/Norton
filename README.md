# Norton - Android Security App

A Norton-inspired Android security application built with Kotlin and Jetpack Compose, featuring a device security scanner and an AI-powered scam detection assistant (Genie). Built as part of the Gen Digital Norton Mobile Engineering AI-First Intern take-home assignment.

> [!NOTE]
> 🎯 **Primary Submission: Option B - Scam Message Detector**
> Out of curiosity, I also built **Option A (Security Health Dashboard)** as a bonus. Both features are fully functional in the app.

---

## Features

### Option B - Genie (Scam Message Detector)
- Paste any suspicious message, URL or email snippet to analyse it instantly
- Powered by Claude Haiku 4.5 via the Anthropic Java SDK with prompt caching (available in sdk)
- Returns risk level (SAFE / SUSPICIOUS / DANGEROUS / UNKNOWN), a confidence score and a plain-english one line explanation
- Offline fallback: regex-based pattern matching when no internet connection is available
- 3 pre-loaded example messages (SMS, EMAIL, URL) for quick testing
- Animated loading state with step-by-step analysis indicators
- Result displayed in a bottom sheet with confidence progress bar

### Option A - Home (Security Health Dashboard) (with mock data)
- Runs four sequential security checks: OS Version, App Threats, Wi-Fi Safety, and Password Strength
- Animated per-check progress: PENDING → SCANNING → COMPLETE
- Spinning circular progress indicator with live percentage counter
- Security score (0–100) with colour-coded circular indicator on results screen
- Results show SECURE / REVIEW status per check

### Shared
- Bottom navigation (Home + Genie)
- Offline banner (slides up above bottom nav when no deice is offline)
- Light and dark theme support

---

## Screenshots

### Genie - Scam Detector

<table>
<tr>
<td align="center"><b>Empty State</b></td>
<td align="center"><b>With Input</b></td>
<td align="center"><b>Result (Safe)</b></td>
<td align="center"><b>Result (Dangerous)</b></td>
<td align="center"><b>Result (Suspicious)</b></td>
</tr>

<tr>
<td align="center">
<img src="screenshots/Genie1-Dark.png" width="160"/>
</td>

<td align="center">
<img src="screenshots/Genie2-Dark.png" width="160"/>
</td>

<td align="center">
<img src="screenshots/Genie3-Dark.png" width="160"/>
</td>

<td align="center">
<img src="screenshots/Genie4-Dark.png" width="160"/>
</td>

<td align="center">
<img src="screenshots/Genie5-Dark.png" width="160"/>
</td>
</tr>

<tr>
<td align="center">
<img src="screenshots/Genie1-Light.png" width="160"/>
</td>

<td align="center">
<img src="screenshots/Genie2-Light.png" width="160"/>
</td>

<td align="center">
<img src="screenshots/Genie3-Light.png" width="160"/>
</td>

<td align="center">
<img src="screenshots/Genie4-Light.png" width="160"/>
</td>

<td align="center">
<img src="screenshots/Genie5-Light.png" width="160"/>
</td>
</tr>
</table>


---

### Home - Security Scanner

<table>
<tr>
<td align="center"><b>Dashboard</b></td>
<td align="center"><b>Scanning</b></td>
<td align="center"><b>Scan Complete</b></td>
<td align="center"><b>Scan Result</b></td>
</tr>

<tr>
<td><img src="screenshots/Scan1-Dark.png" width="220"/></td>
<td><img src="screenshots/Scan2-Dark.png" width="220"/></td>
<td><img src="screenshots/Scan3-Dark.png" width="220"/></td>
<td><img src="screenshots/Scan4-Dark.png" width="220"/></td>
</tr>

<tr>
<td><img src="screenshots/Scan1-Light.png" width="220"/></td>
<td><img src="screenshots/Scan2-Light.png" width="220"/></td>
<td><img src="screenshots/Scan3-Light.png" width="220"/></td>
<td><img src="screenshots/Scan4-Light.png" width="220"/></td>
</tr>
</table>

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVI + Clean Architecture |
| DI | Dagger Hilt |
| Navigation | Jetpack Navigation Compose |
| AI / API | Anthropic Java SDK (Claude Haiku 4.5) |
| Async | Coroutines + Flow |
| Testing | JUnit 5, MockK, coroutines-test |

---

## Project Structure

```
app/src/main/java/com/charan/norton/
│
├── common/
│   ├── components/          # Shared UI (PrimaryButton, TitleText, SubTitleText, BottomNavBar, DeviceOffline)
│   ├── navigation/          # NavGraph, Screen routes
│   ├── network/             # NetworkChecker interface + AndroidNetworkChecker
│   └── theme/               # Color, Typography, Theme
│
├── di/
│   └── AppModule.kt         # Hilt module - repositories, NetworkChecker, AnthropicClient
│
├── features/
│   ├── genie/
│   │   ├── data/repository/ # GenieRepositoryImpl (Anthropic API call + prompt)
│   │   ├── domain/
│   │   │   ├── model/       # ScamResult, RiskLevel
│   │   │   ├── repository/  # GenieRepository interface
│   │   │   └── usecase/     # AnalyseMessageUseCase (online + offline regex fallback)
│   │   └── presentation/
│   │       ├── components/  # InputField, GenieResult, GenieAnalysing, ExampleChip, NortonIconButton
│   │       ├── GenieContract.kt
│   │       ├── GenieScreen.kt
│   │       └── GenieViewModel.kt
│   │
│   └── scan/
│       ├── data/
│       │   ├── datasource/  # MockScanDataSource (structured as real API response)
│       │   └── repository/  # ScanRepositoryImpl
│       ├── domain/
│       │   ├── model/       # SecurityScore, ScanCheck, CheckStatus
│       │   ├── repository/  # ScanRepository interface
│       │   └── usecase/     # RunScanUseCase
│       └── presentation/
│           ├── components/  # CheckItemRow, ScanProgressIndicator, NotScannedIndicator, StatusPill
│           ├── ScanContract.kt
│           ├── HomeScreen.kt
│           ├── ScanScreen.kt
│           ├── ScanResultScreen.kt
│           ├── ScanResultViewModel.kt
│           └── ScanViewModel.kt
│
└── MainActivity.kt
└── NortonApplication.kt
```

---

## Setup Instructions

### Prerequisites

- Android Studio Hedgehog or later
- JDK 11+
- Android SDK with API 36 installed
- An [Anthropic API key](https://console.anthropic.com/)

### 1. Clone the repository

```bash
git clone https://github.com/charanprasanth/Norton.git
cd Norton
```

### 2. Add your Anthropic API key

Create or open `local.properties` in the project root and add:

```properties
ANTHROPIC_API_KEY=sk-ant-xxxxxxxxxxxxxxxx
```

> `local.properties` is git-ignored

### 3. Build and run

Open the project in Android Studio, let Gradle sync complete, then:

- **Run on device/emulator:** Click `Run` button or press `Shift + F10`

### 4. Run tests

```bash
./gradlew test
```
---

## Architecture Overview

The app follows **Clean Architecture** with an **MVI** presentation pattern.
```
Presentation
(ViewModel, Screen)
      ↓
   Domain
(UseCase, Repository interface, Models)
      ↑
    Data
(Repository, DataSource)
```

- **Domain** has zero Android dependencies - pure Kotlin interfaces, models and use cases
- **Data** implements domain interfaces; all Android-specific code lives here
- **Presentation** holds ViewModels (Hilt-injected), state, actions and Compose screens

---

## My AI Journey With This Project

Before writing a single line of code, I started with Claude Design to visualise the screens. I used ChatGPT to optimise my initial rough prompt into something more structured, then fed it to Claude Design to generate the mockups.

**Design Prompt (optimised the prompt with ChatGPT):**

```
I'm building an app like norton 360 security app with two features. design simple, clean UI screens for these:

feature1: GENIE (scam detection)
- one main screen with:
  1. input field (placeholder: "paste suspicious message, email or link")
  2. 3 example scam message buttons below (quick-tap to populate input)
  3.  `Analyze` button (primary CTA)
  4. show loader state
  5. bottom sheet showing results after analyzing with he following info
    - risk level badge (safe - green, suspicious = yellow, dangerous = red
    - confidence score (percentage)
    - one line explanation
    - retry button
design approach - minimalist, card-based with focus on readability
feature2: SCAN (security health dashboard)
- screen1 - dashboard:
  show `not scanned` and add `scan now` button - primary CTA and add a message like `tap to scan your device`
- screen2 - scanning:
  1. show scanning animation with scan progress
  2. list 4 security checks below, like `os version`, `app threats`, `wiffi`, with icon and small description -> scanning one by one
- screen3 - scan results
  1. show final score
  2. show status of all check done
  3. overall summary (example - device is secure)
 
bottom navigation bar with two tabs (HOME-scan, GENIE-scam detection) connect both feature1 and feature2
- use simple material icons and highlight active tab
- create jetpack compose friendly design and poppins font family
- color palette: primary - blue/purple, success - green, warning-yellow, danger-red, neutral - grey
- no heavy animations or complex ui

also provide design mockups for each screen, color palette and typography guide with font sizes and weights
```

Claude Design returned full screen mockups, a complete color palette with hex codes, and a typography scale - which I used directly when writing `Color.kt` and `Type.kt`. This was the foundation everything else was built on.

From there I moved to Claude (claude.ai) for architecture planning, then to Claude Code for implementation. Each tool played a different role:

- **ChatGPT** - prompt refinement
- **Claude Design** - screen design and design system
- **Claude (claude.ai)** - architecture decisions, code review, debugging
- **Claude Code** - implementation, file generation, refactoring

The prompts below document the most significant AI interactions during the coding phase.
 
---

## AI Interaction Log

Throughout the project I used Claude (claude.ai and Claude Code) as my primary AI assistant. I used ChatGPT twice for refining prompts. All the changes were approved manually each time
 
---

### Prompt 1 — Bottom navigation and screen wiring

**What I asked:**
```
use /android-compose-ui skills to build bottom navbar, ic_home and ic_genie icons
are added in the drawable folder based on the attached design in dark mode and light mode.
Add dependencies for compose navigation and wire up the screens via navgraph.
```

**What Claude returned:**
Navigation 2.x style code using `sealed class Screen(val route: String)` with drawable icons wired into a `NavHost`.

**My refinement:**
After an AI code review flagged string-based routes as the old Nav 2.x style, I migrated to type-safe navigation. I also removed the drawable icons and switched to `Icons.Outlined.Home` from Material Icons Extended - simpler and consistent with the design system. Made additional color tweaks to match the expected active/inactive states from the design.
 
---

### Prompt 2 — Dagger Hilt setup

**What I asked:**
```
Add ksp 2.3.7 dependency. Add latest version of dagger hilt and hilt navigation
dependencies and setup the codebase for dependency injection with AppModule.
```

**What Claude returned:**
Full Hilt setup across `libs.versions.toml`, `build.gradle.kts`, `AppModule.kt`, and `NortonApplication.kt`.

**My refinement:**
Hit a KSP version conflict — the version Claude suggested wasn't compatible with my Kotlin version. I manually looked up the correct KSP version for Kotlin 2.x and updated both `libs.versions.toml` and the plugin declaration. The rest of the Hilt setup was used as-is.
 
---

### Prompt 3 — Genie AI integration

**What I asked:**
```
Add gemini sdk on the app for the genie feature. When the analyse button is clicked
on the genie screen, disable the button and perform the call with gemini sdk to find
whether the input sent from genie screen is spam or not. Print the result on logcat
with TAG: SCAM_RESULT.
```

**What Claude returned:**
Changes across 6 files: `libs.versions.toml`, `build.gradle.kts`, `AppModule.kt`, `GenieContract.kt`, `GenieViewModel.kt`, and `GenieScreen.kt` — all wired up for the Gemini SDK.

**My refinement:**
I didn't have Gemini API credits, so I switched to the Anthropic SDK (Claude Haiku) instead. The architecture Claude generated was reused entirely — I only swapped the SDK dependency and the API call implementation. Also later moved the API call out of the ViewModel into a `GenieRepository` after a code review flagged the violation.
 
---

### Prompt 4 — GenieResult bottom sheet composable

**What I asked:**
```
Create a composable for the result named GenieResult. One common composable,
differentiate based on params like constructor overloading. It should have a status
pill for SAFE, SUSPICIOUS, DANGEROUS; a title; confidence score with a seekbar-like
design; description; and an Analyse another button (primary button).
```

**What Claude returned:**
A working composable with a `RiskUi` private data class mapping each `RiskLevel` to label, icon, color, and headline — all three variants sharing the same layout.

**My refinement:**
- Replaced `LinearProgressIndicator` with a `Box`-based custom bar — Material3's indicator had a visual artifact (small dot at the end of the track)
- Set `dragHandle = null` on `ModalBottomSheet` to prevent swipe-to-dismiss
- Claude attempted to use `MaterialTheme` colors inside a non-composable function — caught and fixed manually
- Claude used a deprecated icon (`Icons.Outlined.HelpOutline`) for the UNKNOWN case — replaced with a valid alternative
---

### Prompt 5 — COMPLETE status for scan checks

**What I asked:**
```
On the scanning screen, when completing each check, the scan status for that specific
check should not show SECURE or REVIEW. It should show COMPLETE only. The result
should only appear on the ScanResult screen. Add COMPLETE to the enum for this.
```

**What Claude returned:**
Changes across `CheckStatus` enum, `ScanViewModel`, `ScanScreen`, `StatusPill`, and `CheckItemRow` — all updated consistently.

**My refinement:**
Used as-is. The output was clean and covered all the affected files in one pass without me having to follow up. This was one of the better Claude Code responses in the project — full context awareness, no regressions.
 
---

### Prompt 6 — Designing the Genie system prompt

**What I asked:**
```
design a system prompt for a scam detection engine that returns structured output,
the model should return risk level (SAFE/SUSPICIOUS/DANGEROUS), a confidence score
0-100 and a plain english reason and the output should be easy to parse with regex
```

**What Claude returned:**
A JSON-based prompt that instructed the model to return `{"risk":"...","confidence":...,"reason":"..."}`.

**My refinement:**
I switched from JSON to plain text format (`risk: SAFE\nconfidence: 95\nreason: ...`) after realising that `JSONObject` is an Android class and fails in JVM unit tests. Plain text with regex parsing works everywhere without extra libraries. I also added explicit classification rules, red flag examples, and a special case for gibberish input.

**What I learned:** Prompt format choices have downstream engineering implications — what's convenient for parsing in production might break your test suite.
 
---

## AI Code Review Summary

Before submitting, I asked Claude to review the full codebase. It returned a prioritised list of issues.

**Issues fixed:**

1. **Dead code in offline analyser** — `suspiciousPatterns` was defined but never used in the `when` block. Every non-dangerous offline input was defaulting to `SUSPICIOUS` regardless of whether it matched suspicious patterns. Fixed the `when` block to check both lists correctly.
2. **`object` instead of `data object` in sealed classes** — `GenieAction` and `ScanAction` used `object` for singleton actions, missing `toString`, `equals`, and `hashCode`. Changed to `data object` across both contracts.
3. **Missing `Event` type in MVI contracts** — Neither `GenieContract.kt` nor `ScanContract.kt` had a sealed `Event` interface. Added both to keep the pattern consistent for future additions.
4. **String-based navigation routes** — The `sealed class Screen(val route: String)` approach is Nav 2.x style. Migrated to type-safe navigation with `@Serializable` objects.
   **Issues reviewed and intentionally kept:**

- **Mocks over fakes in tests** — Claude suggested hand-written fakes instead of MockK. Kept MockK — the test scope didn't warrant the overhead of maintaining separate fake implementations.
- **`LaunchedEffect(Unit)` auto-starting the scan** — Flagged as removing user agency. Kept intentionally — `startScan()` guards against re-entry, so navigating back doesn't restart the scan.
- **`CheckStatus` having both `SECURE` and `COMPLETE`** — Flagged as inconsistent. Kept by design — `SECURE` is the domain result from the data source, `COMPLETE` is a presentation-only state for the scan animation. They serve different layers.
---

## Reflection

### What did you learn?

- **Writing unit tests surfaced design problems I wouldn't have caught otherwise.** When my `JSONObject`-based parser failed in JVM unit tests, it forced me to rethink the parsing strategy entirely. The regex approach I landed on was simpler, more portable, and more resilient to edge cases like Claude wrapping output in markdown code blocks.
- **AI code review is genuinely useful for catching things you stop seeing.** I asked Claude to review the codebase before submitting and it caught four real issues: dead code in the offline pattern matcher, sealed class actions using `object` instead of `data object`, missing `Event` type in MVI contracts, and string-based navigation routes. I fixed all four. The rest of the feedback I reviewed and intentionally kept as-is — for example, keeping MockK over fakes because the test scope didn't warrant the overhead.
- **Working with AI daily changes how you allocate attention.** The boilerplate — DI setup, repository scaffolding, contract files — came quickly with AI assistance. The time I saved went into architecture decisions, edge cases, and reviewing AI output critically rather than accepting it blindly.
### What would I do differently?

- **Write tests earlier.** I wrote most tests after the implementation was done. If I had written them in parallel, I would have caught the `JSONObject` issue much earlier and designed the parsing interface around testability from the start.
- **Be more specific in prompts upfront.** Some back-and-forth with Claude Code could have been avoided with better initial context — being explicit about existing file names, naming conventions, and decisions already made earlier in the session.
- **Use Claude Design before writing a single line of code.** Starting with Claude Design gave me screen mockups, a color palette, and a typography scale to work from — but I didn't fully appreciate how much that would shape the implementation until I was already coding. Next time I'd treat the design phase as a proper planning step, not just a starting point. One thing worth noting — I followed the Claude Design mockups around 90%, not 100%. Some specific icons and vectors from the design were hard to source exactly, so I substituted the closest available Material icons. The overall layout, color palette, and typography were followed precisely.
- **Bridge the gap between Claude Code and Claude web app more deliberately.** Claude Code in Android Studio doesn't accept images, so whenever I needed to share a screenshot — a UI issue, a design reference, an error state — I had to switch to the Claude web app, explain the context there, get a prompt, then bring that back to Claude Code. It worked, but maintaining the same context across two places was friction I hadn't anticipated. Next time I'd document the shared context somewhere I can paste quickly into either tool.
---

## Video Walkthrough

> 📹 _Link will be added before final submission._
 