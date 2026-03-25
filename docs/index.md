# Project Index: Budget Insight System (BIS)

Welcome to the central documentation hub for the **Budget Insight System (BIS)**. This project is a personal budget management tool developed as a university project with a focus on **Layered Architecture** and **ORM-based data persistence**.

---

## 📋 Core Documentation
* [**Project Specification**](./specification.md): Detailed overview of the system scope, technical constraints, and the six core functional requirements (F1–F6).
* [**Non-Functional Requirements (NFR)**](./nfr.md): Specifications for system quality attributes, including **Data Integrity**, **Unit Testing**, and **Usability**.
* [**Use Case Descriptions**](./UseCases.md): Comprehensive, step-by-step logical flows for every system feature, following the standardized **Cockburn template**.

---

## 📊 Visual Models & UML
* [**Feature Flow Diagram**](./diagrams/flow_diagram.md): A visual representation of user navigation paths and feature interactions within the system.
* [**Use Case Diagram**](./diagrams/use_case_diagram.md): A logical map of actors (User and File System) and their relationships to the system's functional goals.
* [**StarUML Source Models**](./models): Access to the native `.mdj` files, ensuring compliance with the **UML metamodel** requirement.

---

## 🛠️ Technical Standards
* **Layered Architecture**: Documentation on the separation of the Presentation, Business, and Data layers.
* **ORM Usage**: Details on how Object-Relational Mapping is utilized for database interactions and persistence.
* **Business Rules**: Enforcement of expense limits and categorization logic within the Business layer.

---

### How to Navigate this Repository
1.  **Understand the Scope**: Review the [Specification](./specification.md) for functional and technical boundaries.
2.  **Explore User Interactions**: Read the [Use Case Descriptions](./diagrams/use_case_diagram.md) to understand the step-by-step logic of the system.
3.  **Verify the Model**: Open the native files in the `/models` directory using **StarUML** to inspect the underlying UML structure.