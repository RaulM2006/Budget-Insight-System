# Budget Insight System - Project Specification

**Project Code**: BIS (Budget Insight System)

## Overview
A simplified library management system designed for personal use, allowing users to track personal budget, set spending limits and generate overviews of personal spendings. The system does not include administrative interfaces; instead, data is managed through file imports.

## Scope
This is a university project focused on core library functionality without complex administrative features. The system emphasises user interactions with the budget manager and overviewing tool.

## Features
For a visual representation of how features interact and the user flow, see the [Feature Flow Diagram](./diagrams/flow_diagram.md).

### F1. Import Functionality
- **Spendings and Users**: All user accounts and their corresponding spendings are created and updated via import from CSV files
- **No Admin Interface**: There is no administrator role; all data management happens through file imports
- **Batch Updates**: Changes to user information are applied by importing updated files
    
### F2. Authentication
- **Login**: Users authenticate with their credentials to access the system
- **Logout**: Users can safely log out of their session
    
### F3. View/Export Spendings
- **View Monthly Spendings**: Users can see a complete report of their spendings during a certain month
- **Export Monthly Spendings**: Users can export their monthly spendings for a certain month in CVS/XSL/TXT format

### F4. Personal Expenses Managing
- **Spending Flow**: The spending process follows the following pattern:
    - Users can add an expense with specified date, value and optionally a category
    - Users can update a past expense by modifying the date, value or/and category
    - Users can remove a past expense by viewing past expenses based on the date they were added
        
### F5. Expenses Categorization
- **Viewing Categories**: Users can view how much they spent in a specified duration of time per specified category

### F6. Categories Budgeting
- **Budget Limits**: Users can set a budget limit per a category.
- **Limit Status**: Clear indication of limit usage per said category
- **Limit Reached Warning**: Once the limit is reached for a certain category, the system, issues a warning
    
## Non-Functional Requirements

- **Usability**: Simple and intuitive interface for users
- **Data Integrity**: Proper validation of imported data
- **Limit Tracking**: Accurate real-time tracking of budget limits status
- **Testing**: Unit tests must be created to validate core functionality and business logic
- **Optional Enhancements**:
    - Run tests in a CI/CD pipline for automated validation
    - Containerization using Docker/Docker Compose for simplified deployment and development environment setup

## Technical Constraints
- Simple architecture suitable for a university project
- File-based data import (CSV/JSON) instead of complex admin panels
- Focus on core functionality: authentication, filtering, and expenses management
- Clear business rules enforcement (expense limit/category)
- **UML Modeling**: A [UML (Unified Modeling Language)](https://www.omg.org/spec/UML/) model must be created before implementation
  - The model must be created using a tool that has a [UML metamodel](https://www.omg.org/spec/UML/2.5.1/About-UML/) behind it
  - The model should include class diagrams, sequence diagrams, and other relevant UML diagrams
  - Recommended tools: Enterprise Architect, Visual Paradigm, StarUML, or similar UML-compliant tools
- **ORM Usage**: Implementation must use an Object-Relational Mapping (ORM) framework for database interactions
- **Layered Architecture**: Application must be structured with separate layers:
  - Presentation layer (UI/interface)
  - Business layer (business logic and rules)
  - Data layer (database access and persistence)
- **OOP Language**: Implementation must use an Object-Oriented Programming language