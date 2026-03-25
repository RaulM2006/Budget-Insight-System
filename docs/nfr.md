# Non-Functional Requirements: Budget Insight System (BIS)

## 1. Technical Constraints & Architecture
* **Layered Architecture**: The application must be strictly structured into separate Presentation (UI), Business (logic/rules), and Data (persistence) layers.
* **ORM Usage**: Database interactions must be handled through an Object-Relational Mapping (ORM) framework.
* **OOP Language**: The implementation must be written in an Object-Oriented Programming language.
* **UML Modeling**: All system designs must be modeled using a tool with a formal UML metamodel before implementation starts.
* **Data Persistence**: The system must support file-based data imports (CSV/JSON) as the primary method for data management.

---

## 2. Quality Attributes

### 2.1 Usability & Interface
* **Intuitive Design**: The system must provide a simple and intuitive interface for personal budget management.
* **Feedback Mechanism**: The system must issue a clear visual warning immediately when a category budget limit is reached.

### 2.2 Data Integrity & Accuracy
* **Validation**: All imported data from CSV files must undergo proper validation to ensure schema consistency and prevent corruption.
* **Numerical Precision**: All financial calculations must be performed using high-precision decimal types to avoid floating-point rounding errors in budget totals.
* **Real-time Tracking**: Limit status usage per category must be tracked accurately in real-time.

### 2.3 Security & Privacy
* **Access Control**: Authentication is required for users to access the system and their specific data.
* **Session Integrity**: Users must be able to safely log out to terminate their active session.
* **Data Isolation**: The system must ensure that users can only interact with their own spending data, maintaining privacy in a multi-user database environment.

### 2.4 Maintainability & Reliability
* **Decoupling**: Business rules, such as expense limits and category logic, must be enforced within the Business Layer to ensure the system is easy to update.
* **Persistence Integrity**: In the event of a failed batch update, the system must maintain the last known good state of the user data.

---

## 3. Testing & Deployment
* **Unit Testing**: Unit tests must be created to validate all core functionality and business logic (e.g., limit calculations).
* **Automated Validation (Optional)**: Tests should ideally run in a CI/CD pipeline for automated verification.
* **Containerization (Optional)**: The system can be packaged using Docker and Docker Compose to simplify deployment and environment setup.