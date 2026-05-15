---
Summary

Norton is a security-focused Android app with two features:
- Genie — AI-powered scam detection using Claude Haiku via the Anthropic SDK. Includes offline fallback with regex-based heuristics.
- Scan — A simulated device security scanner (mock data) that animates through checks and shows a score.

Architecture is Hilt + MVI contracts + Compose + Clean Architecture layers. Overall the code is clean and well-structured.

---
Issues to Fix

1. suspiciousPatterns is dead code — localAnalyse never uses it

AnalyseMessageUseCase.kt:31-48

The list is defined but the when block only checks dangerousPatterns, then defaults to SUSPICIOUS for everything. Every offline non-dangerous input is always SUSPICIOUS
regardless of whether it matches suspicious patterns.

// The when block should be:
return when {
dangerousPatterns.any { it.containsMatchIn(text) } -> ScamResult(RiskLevel.DANGEROUS, 70, "...")
suspiciousPatterns.any { it.containsMatchIn(text) } -> ScamResult(RiskLevel.SUSPICIOUS, 50, "...")
else -> ScamResult(RiskLevel.SAFE, 60, "No strong indicators detected offline.")
}

---
2. GenieScreen doesn't follow the Root/Screen pattern

GenieScreen.kt:34-40

Per your CLAUDE.md, each screen needs a Root (holds ViewModel, observes events) and a Screen (pure state + onAction, previewable). Currently GenieScreen directly injects the
ViewModel, making the previews (GenieScreenPreviewLight/Dark) broken — they call GenieScreen() which tries to inject Hilt in a preview context.

Rename GenieScreen → GenieRoot, and GenieContent → GenieScreen. Point the previews at GenieScreen(state, onAction).

---
3. sealed class Actions should use data object

GenieContract.kt:14-18, ScanContract.kt:15-16

// Current
object OnAnalyse : GenieAction()
object OnDismissResult : GenieAction()
object StartScan : ScanAction()

// Should be
data object OnAnalyse : GenieAction()
data object OnDismissResult : GenieAction()
data object StartScan : ScanAction()

data object gives proper toString, equals, and hashCode — important for sealed class exhaustion and testing.

---
4. No Event type in either feature contract

Both GenieContract.kt and ScanContract.kt are missing the Event sealed interface. Your pattern requires State + Action + Event. While neither feature currently needs navigation
events from the ViewModel side, adding it now keeps the pattern consistent and makes future additions clean.

---
5. ScanResultViewModel reads shared mutable state from a singleton

ScanResultViewModel.kt:14

val scanResult: SecurityScore? = repository.getLastScanResult()

This reads from MockScanDataSource.lastResult — a nullable field set as a side effect of runScan(). If the ViewModel is recreated before a scan completes, it returns null. The
coupling between ScanViewModel and ScanResultViewModel via a shared mutable singleton is fragile. Pass the score through navigation arguments instead (even just the score int),
or use a proper in-memory cache in the repository.

---
6. Navigation uses string routes — not type-safe

NavConstants.kt, NavGraph.kt

Your CLAUDE.md pattern uses @Serializable data classes/objects for type-safe nav. The current sealed class Screen(val route: String) approach is the old Nav 2.x style. Not
urgent, but worth migrating when you next touch navigation.

---
7. Test docblock comments violate your own rules

All test files

Your CLAUDE.md says: "Never write multi-paragraph docstrings or multi-line comment blocks." The /** Purpose... Author... */ blocks on every test method should be removed. Test
names are already descriptive enough.

---
8. Tests use mockk instead of fakes

GenieViewModelTest.kt:25, ScanViewModelTest.kt:29, etc.

Your CLAUDE.md says "Fakes over mocks." The use case and repository tests should use hand-written fakes (like FakeGenieRepository) rather than mockk. It's fine for the use case
test since it's testing parsing logic, but the ViewModel tests are good candidates for fakes.

---
Minor Observations

- scanningDescriptions in ScanViewModel.kt:85-90 — hardcoded strings in the ViewModel should be string resources.
- ScanScreen.kt:52 — LaunchedEffect(Unit) auto-starts the scan, which is fine since startScan() guards against re-entry, but it removes user agency. Consider whether this should
be triggered from HomeScreen instead.
- CheckStatus has both SECURE and COMPLETE — the data source uses SECURE/WARNING, but the scan animation uses COMPLETE. This is a bit inconsistent; COMPLETE essentially replaces
SECURE after the animation ends.

---
Priority order: Fix #1 (bug — dead code with wrong behavior), then #2 (broken previews), then #3–4 (pattern consistency).