# Android Dev — Claude Code Custom Instructions

---

## Who I Am

Android developer. My stack:

- **Android**: Kotlin, Jetpack Compose, MVVM + Clean Architecture, Dagger hilt, Coroutines + Flow, Room, Retrofit, DataStore, WorkManager, Firebase
- **Testing**: JUnit5, Turbine, AssertK, UnconfinedTestDispatcher, ComposeTestRule

---

## Non-Negotiable Principles

These are never compromised. Flag violations in any code you write or review:

1. **Null safety everywhere** — No `!!` unless absolutely justified with a comment
2. **Kotlin-first** — New Android code is always Kotlin; Java only for legacy or forced interop
3. **Avoid over-engineering** — The simplest solution that works is correct
4. **Small, reusable composables** — Never one massive widget tree; decompose aggressively
5. **Extensions over utility classes** — Prefer extension methods on types
6. **No God ViewModels** — Business logic lives in use cases / domain layer
7. **Single source of truth** — UI observes state from one place; never duplicate state
8. **Coroutines over callbacks** — No callback hell; use suspend functions and Flow
9. **Environment variables for all secrets** — No hardcoded keys, tokens, or passwords

---

## Android / KMP Patterns

### MVI Presentation Layer

Every screen has: `State` (data class) + `Action` (sealed interface) + `Event` (sealed interface) + `ViewModel`.

```kotlin
// State
data class NoteListState(
    val notes: List<NoteUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null
)

// Action
sealed interface NoteListAction {
    data object OnRefreshClick : NoteListAction
    data class OnNoteClick(val noteId: String) : NoteListAction
}

// Event
sealed interface NoteListEvent {
    data class NavigateToDetail(val noteId: String) : NoteListEvent
    data class ShowSnackbar(val message: UiText) : NoteListEvent
}

// ViewModel
class NoteListViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _state = MutableStateFlow(NoteListState())
    val state = _state.asStateFlow()

    private val _events = Channel<NoteListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: NoteListAction) {
        when (action) {
            is NoteListAction.OnRefreshClick -> loadNotes()
            is NoteListAction.OnNoteClick -> viewModelScope.launch {
                _events.send(NoteListEvent.NavigateToDetail(action.noteId))
            }
        }
    }
}
```

### Composable Structure

Both Root and Screen live in the same file (e.g. `NoteListScreen.kt`).

```kotlin
// Root — holds ViewModel, observes events
@Composable
fun NoteListRoot(
    onNavigateToDetail: (String) -> Unit,
    viewModel: NoteListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is NoteListEvent.NavigateToDetail -> onNavigateToDetail(event.noteId)
        }
    }
    NoteListScreen(state = state, onAction = viewModel::onAction)
}

// Screen — pure state + onAction, previewable
@Composable
fun NoteListScreen(state: NoteListState, onAction: (NoteListAction) -> Unit) { ... }
```

### Compose UI Rules

- UI is dumb — composables render state and forward actions only
- State via `collectAsStateWithLifecycle()`
- `@Stable` only when class has unstable fields (List, Map, interfaces)
- Animations: use `graphicsLayer`, never `Modifier.alpha(animatedValue)` directly
- Lazy lists: add `key = { it.id }` where there is an obvious unique id
- `contentDescription` on all interactive/informational elements via string resources
- TextField state lives in ViewModel; every keystroke dispatches an Action

### Data Layer

- **Data source** = single source (DB or API). **Repository** = multiple sources combined.
- Interfaces in `domain`, implementations in `data`
- DTOs and domain models always separate; mappers as extension functions
- Never suffix implementations with `Impl` — name for what makes them unique (e.g. `RoomNoteDataSource`)

### Error Handling

```kotlin
// Result wrapper (core:domain)
sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.example.Error>(val error: E) : Result<Nothing, E>
}
typealias EmptyResult<E> = Result<Unit, E>

// DataError (core:domain)
sealed interface DataError : Error {
    enum class Network : DataError { BAD_REQUEST, UNAUTHORIZED, NO_INTERNET, SERVER_ERROR, UNKNOWN, /* ... */ }
    enum class Local : DataError { DISK_FULL, NOT_FOUND, UNKNOWN }
}
```

### Koin DI

```kotlin
// Prefer constructor-reference overloads
val notesDataModule = module {
    singleOf(::RoomNoteDataSource) { bind<NoteLocalDataSource>() }
}
val notesPresentationModule = module {
    viewModelOf(::NoteListViewModel)
}
// Assemble all modules in :app Application class
// Always use koinViewModel() in Root composables
```

### Navigation (Type-safe Compose Nav)

```kotlin
@Serializable object NoteListRoute
@Serializable data class NoteDetailRoute(val noteId: String)

fun NavGraphBuilder.notesGraph(navController: NavController, onNavigateToEditor: (String) -> Unit) {
    composable<NoteListRoute> { NoteListRoot(onNavigateToDetail = { navController.navigate(NoteDetailRoute(it)) }) }
    composable<NoteDetailRoute> { backStackEntry ->
        val route: NoteDetailRoute = backStackEntry.toRoute()
        NoteDetailRoot(noteId = route.noteId, onNavigateToEditor = onNavigateToEditor)
    }
}
```

### Module Structure

```
:app
:core:domain        ← Shared domain models, interfaces, Result, DataError
:core:data          ← Ktor HttpClient factory, safe call helpers
:core:presentation  ← ObserveAsEvents, UiText
:core:design-system ← Reusable composables, theme, typography
:feature:<name>:domain
:feature:<name>:data
:feature:<name>:presentation
```

Dependency rule: `presentation` → `domain` ← `data`. Domain depends on nothing.

### Testing

```kotlin
// ViewModel test setup
val testDispatcher = UnconfinedTestDispatcher()
@BeforeEach fun setUp() { Dispatchers.setMain(testDispatcher) }
@AfterEach fun tearDown() { Dispatchers.resetMain() }

// Test state with Turbine
viewModel.state.test {
    viewModel.onAction(NoteListAction.OnRefreshClick)
    assertThat(awaitItem().isLoading).isTrue()
}

// Fakes over mocks
class FakeNoteRepository : NoteRepository {
    var shouldReturnError = false
    override suspend fun getNotes() = if (shouldReturnError)
        Result.Error(DataError.Local.UNKNOWN) else Result.Success(emptyList())
}
```

---

## Code Review Checklist

### All Code
- [ ] No hardcoded secrets or API keys
- [ ] No dead code or commented-out blocks
- [ ] Error cases handled explicitly
- [ ] Naming clear and consistent

### Android / Kotlin
- [ ] No `!!` force-unwrap without comment
- [ ] No `GlobalScope` — `viewModelScope` or `lifecycleScope` only
- [ ] No Context leaked into ViewModel
- [ ] No business logic in ViewModel (use cases)

---

## Debugging Approach

1. Read the error first — stack trace tells you where to look
2. Reproduce before fixing
3. Isolate the variable — change one thing at a time
4. Check the obvious: wrong env var, stale build, cached state

### Android Quick Checks
- ANR → work on main thread, use `Dispatchers.IO`
- Memory leak → LeakCanary, Context held in ViewModel
- Compose recomposition loop → state change inside composition, move to `LaunchedEffect`