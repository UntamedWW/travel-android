# Project Status & Tasks

Main focus:
- change Budget to Expense (front and back), 
- add plannedBudget as property of Trip

## Today's Focus
- [ ] **Repository Layer Implementation**
    - [ ] Create `AuthRepository` (Handle login/register logic & token saving).
    - [ ] Create `TravelRepository` (Manage Trips, Budget, and Itinerary data).
- [ ] **Business Logic (ViewModels)**
    - [ ] Implement `AuthViewModel` (State management for Login/Register).
    - [ ] Implement `HomeViewModel` (Fetching the next trip and progress for Dashboard).
- [ ] **UI & Navigation Foundation**
    - [ ] Set up `NavHost` and define routes (Auth, Home, TripDetails).
    - [ ] Create reusable UI components (Custom Buttons, Cards, Progress Bars).
- [ ] **Feature Implementation**
    - [ ] Build functional Login and Registration screens.
    - [ ] Build Home Screen UI with real data integration.
- [ ] **Maintenance**
    - [ ] Remove legacy `*Dto.kt` files to clean up the data layer.

## Screen Structure
├── auth (Login/Register)
├── home (Dashboard)
├── trips (List of all trips)
├── packing (Checklist)
├── budget (Expenses & Planning)
├── itinerary (Daily schedule)
└── profile (User settings)
