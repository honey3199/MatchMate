# MatchMate
MatchMate is an Android app simulating a matrimonial platform, fetching user data from the randomuser.me API, displaying match cards, and supporting offline functionality. The app allows Accept/Decline interactions, persists data locally, and includes a match score algorithm.

# Project Setup Instructions

1. Clone the repository: git clone https://github.com/honey3199/MatchMate/.

2. Open the project in Android Studio.

3. Sync Gradle to download dependencies listed in build.gradle.

4. Run the app on an emulator or physical device with API 21+.

# Library Used
Retrofit (2.9.0): Chosen for its type-safe HTTP client and simplicity in handling API calls to randomuser.me.
Room (2.5.0): Used for local database persistence, ensuring offline availability of user data and Accept/Decline states.
Glide (4.15.1): Selected for efficient image loading from the API, with caching for performance.
Kotlin Coroutines & Flow: Employed for asynchronous operations and reactive data handling, ideal for pagination and UI updates.
Hilt (2.52): Dependency Injection	Simplifies injection and architecture

# Architecture Explanation

MVVM: Adopted for its separation of concerns, enabling testable business logic in ViewModels and reactive UI updates with StateFlow. This architecture suits the app's dynamic data requirements and offline mode, as it decouples data persistence (Room) from presentation.

# Justification for Added Fields

1. Education: Added to reflect a user's academic background, a key factor in matrimonial compatibility. Defaulted to "Graduate" since the API lacks this data; in a real app, users would provide it.
2. Profession: Included to indicate career status, relevant for partner matching. Defaulted to "Job" as a placeholder, to be user-specified in production.

# Match Score Logic Description

The match score (0-100) is calculated based on age proximity and city match:
Age Difference Contribution: The score is based on the absolute difference in age between the user and the match, assigned as follows:
70 points if the age difference is ≤ 2 years.
50 points if the age difference is ≤ 5 years.
30 points if the age difference is ≤ 8 years.
10 points if the age difference is > 8 years.

City Match Contribution: Adds 30 points if the cities match (case-insensitive), 0 otherwise.
Formula: score = ageScore + cityScore, with the result coerced to the range 0-100.
Example: For a 45-year-old user and a 40-year-old match in the same city, age difference is 5 years (50 points) + city match (30 points) = 80.

# UI/UX Design

The app features a card-based UI with RecyclerView, displaying user images, name, age, profession, education, location, and match score.
Cards use a cyan gradient background (#E0F7FA to #B2EBF2), circular profile images with borders, and teal accents (#0288D1) for text and buttons.
Accept/Decline buttons update the UI with "SELECTED" (green) or "REJECTED" (red) tags, persisted in Room and reflected dynamically.
The layout follows Material Design principles with consistent margins (16dp) and rounded corners (16dp).

# Design Constraint Handling

No Profile Images: If legally required, the profile_image ImageView would be replaced with a placeholder (e.g., initials in a colored circle). The UI would adapt by increasing text prominence (e.g., name and details). This change would be documented to maintain compliance and usability.

# Reflection and Hypothetical Improvement

Feature Addition: If given more time, I would implement a "Preferences Filter" allowing users to filter matches by education, profession, or location. This would enhance user experience by personalizing the match list, requiring additional Room tables for preferences and a refined API query mechanism.
