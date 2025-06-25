# octocat
A multi-module, Jetpack Compose (Android) codebase that fetches, pages, and displays cat images (with breed metadata) from The Cat API.

## Approach
1.**Platform-agnostic network layer**
  - The app code depends only on a `NetworkDatasource` interface, not Retrofit directly.
  - You could swap in **Ktor**, **OkHttp**, or any other HTTP client by providing a new implementation of `NetworkDatasource`.

2.**Paging 3 for infinite scroll**
  - A custom `BreedPagingSource` drives `/v1/images/search?has_breeds=1&page=X&limit=Y`, maps DTOs → `CatModel`, and emits `PagingData<CatModel>`.
  - Compose’s `LazyColumn` + `collectAsLazyPagingItems()` renders items + load-state spinners/errors

2.**Dependency Injection with Hilt**
  - Modules provide Retrofit, `NetworkDatasource`, `BreedRepository`, and feature ViewModels.
  - All classes are easy to mock or swap in tests because they depend only on interfaces.

4.**Test-coverages**
  - **Unit tests** cover:
    - `NetworkDataSourceImpl` 
    - `BreedPagingSource` 
    - `BreedRepositoryImpl`
    - `BreedListViewModel` 

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


## What I’d Add Next

- **Build script consolidation**  
  Extract common Gradle plugin/configuration into a shared Gradle convention plugin to DRY up module `build.gradle` files.
- **Server-side breed filtering**  
  If API tier allows, pass `breed_ids=<id>` to avoid client-side filtering and optimize bandwidth.
- **Offline caching**  
  Layer in Room or SQLDelight for persisting pages, and serve from local DB when offline.