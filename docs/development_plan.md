# Development Plan: Budget Insight System (BIS)

This document outlines the iterative development process for the BIS project, ensuring that core architectural constraints—such as the **Layered Architecture** and **ORM usage**—are established early.

---

## 🏗️ Iteration 1: Foundations & Authentication
**Goal**: Establish the system architecture, database schema, and secure access.

### 🔑 Key Tasks
* **Architecture Setup**: Initialize the project structure with defined Presentation, Business, and Data layers.
* **Database & ORM**: Configure the ORM framework and create initial entities for `User` and `Expense`.
* **F2. Authentication**: Implement the Login and Logout functionality, ensuring passwords are securely handled.
* **F1. Initial Data Import**: Develop the base logic for processing CSV files to populate user accounts and initial spending data.

**Deliverable**: A functional application where a user can log in and view an empty or imported dashboard.

---

## 💰 Iteration 2: Expense Management & Categorization
**Goal**: Implement the core "CRUD" functionality and data organization.

### 🔑 Key Tasks
* **F4. Personal Expenses Managing**: Implement the logic to Add, Update, and Remove expenses. Each expense must be linked to the authenticated user via the ORM.
* **F5. Expenses Categorization**: Implement the Business layer logic to group expenses by category over specified durations.
* **F3. Monthly View**: Create the Presentation layer components to display a complete report of monthly spendings.

**Deliverable**: A system where users can manually manage their daily spending and view their habits by category.

---

## 📈 Iteration 3: Budgeting, Exporting & Refinement
**Goal**: Add advanced business logic, reporting, and non-functional polish.

### 🔑 Key Tasks
* **F6. Categories Budgeting**: Implement the "Limit Reached Warning" logic. This requires the Business layer to calculate real-time usage against set limits whenever an expense is added.
* **F3. Data Export**: Enable the export of monthly reports into CSV, XSL, and TXT formats via the File System actor.
* **Testing & Integrity**: Finalize **Unit Tests** for core business rules (especially limit checks) and ensure data validation for all imports.
* **Deployment (Optional)**: Containerize the application using Docker and Docker Compose for a standardized environment.

**Deliverable**: A feature-complete system that alerts users of budget overages and allows for external data reporting.

---

## 🛠️ Iteration Tracking
| Iteration | Status | Primary Focus |
| :--- | :--- | :--- |
| **Iteration 1** | 📝 Planned | Architecture & Auth |
| **Iteration 2** | 📝 Planned | Expense CRUD & Categories |
| **Iteration 3** | 📝 Planned | Budget Logic & Exporting |