# octocat

## 🚀 Approach

1. **Clean, feature-based modules**
  - **`:core:model`** holds pure Kotlin data classes (`CatModel`, `Breed`) with no Android or DI.
  - **`:core:network`** declares a `CatApiService` Retrofit interface and matching serializable DTOs.
  - **`:core:common`** provides `NetworkResult` + `safeNetworkCall` to wrap errors in a uniform sealed type.
  - **`:core:data`** implements `BreedRepository` and a `PagingSource` that uses the network interface → domain mappers.
  - **Features** (`:feature:breed-list` & `:feature:breed-details`) consume only domain classes, Compose UIs, and ViewModels.

2. **Platform-agnostic network layer**
  - The app code depends only on a `NetworkDatasource` interface, not Retrofit directly.
  - You could swap in **Ktor**, **OkHttp**, or any other HTTP client by providing a new implementation of `NetworkDatasource`.

3. **Paging 3 for infinite scroll**
  - A custom `BreedPagingSource` drives `/v1/images/search?has_breeds=1&page=X&limit=Y`, maps DTOs → `CatModel`, and emits `PagingData<CatModel>`.
  - Compose’s `LazyColumn` + `collectAsLazyPagingItems()` renders items + load-state spinners/errors with almost zero UI boilerplate.

4. **Dependency Injection with Hilt**
  - Modules provide Retrofit, `NetworkDatasource`, `BreedRepository`, and feature ViewModels.
  - All classes are easy to mock or swap in tests because they depend only on interfaces.

5. **Test-first mindset**
  - **Unit tests** cover:
    - `NetworkDataSourceImpl` delegates correctly and propagates errors.
    - `BreedPagingSource` boundary logic (`nextKey`/`prevKey`, error cases).
    - `BreedRepositoryImpl` emits a `Flow<PagingData<…>>`.
    - `BreedListViewModel` wires up the repository and honors the page-size constant.
  - Shared test fixtures live in a `testFixtures` source set, so no large DTO definitions are copy-pasted.

| Gradle Module                | What it owns                                                                                                                                                                       | Depends on                                                                                                               |
|------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|
| **`:app`**                   | Main Android application entry-point: sets up Hilt, hosts the `NavDisplay`/NavHost, wires together `feature:breed-list` & `feature:breed-details`, applies the design system theme | `implementation` on **`:core:designsystem`**, **`:feature:breed-list`**, **`:feature:breed-details`**, **`:core:model`** |
| **`:core:model`**            | Pure Kotlin domain models: `CatModel`, `Breed`, `Weight`, etc. — no Android or DI dependencies                                                                                     | *None*                                                                                                                   |
| **`:core:designsystem`**     | Reusable Compose UI atoms: color palettes, typography, themes, and common composable                                                                                               | *None*                                                                                                                   |
| **`:core:common`**           | Generic utilities & error-handling: `NetworkResult` sealed class, `safeNetworkCall`, `handleNetworkError`, `convertErrorBody`                                                      | *None*                                                                                                                   |
| **`:core:network`**          | Networking layer: Retrofit `CatApiService`, DTOs matching The Cat API, Kotlinx-Serialization setup                                                                                 | **`:core:model`**                                                                                                        |
| **`:core:data`**             | Data layer & repositories: `BreedRepositoryImpl`, `BreedPagingSource`, DTO→domain `toDomain()` mappers, paging configuration                                                       | **`:core:model`**, **`:core:network`**, **`:core:common`**                                                               |
| **`:feature:breed-list`**    | Breed-list feature: `BreedListViewModel` (exposes `Flow<PagingData<CatModel>>`), `CatListScreen` composable with infinite-scroll paging                                            | **`:core:data`**, **`:core:designsystem`**                                                                               |
| **`:feature:breed-details`** | Breed-details feature: `BreedDetailScreen` composable renders a single `CatModel` passed from the list with full breed info                                                        | **`:core:model`**, **`:core:designsystem`**                                                                              |


## 🔧 What I’d Add Next

- **Build script consolidation**  
  Extract common Gradle plugin/configuration into a shared Gradle convention plugin to DRY up module `build.gradle` files.
- **Server-side breed filtering**  
  If API tier allows, pass `breed_ids=<id>` to avoid client-side filtering and optimize bandwidth.
- **Offline caching**  
  Layer in Room or SQLDelight for persisting pages, and serve from local DB when offline.
- **End-to-end tests**  
  Add Compose UI tests to verify navigation and load-state UIs under various network scenarios.
- **iOS/multiplatform**  
  Because the network/data layers are platform-agnostic, I could extract them into a Kotlin Multiplatform module and ship an iOS version with SwiftUI.