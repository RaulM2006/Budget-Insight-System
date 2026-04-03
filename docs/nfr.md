# Non-Functional Requirements: Budget Insight System (BIS)

## 1. Technical Constraints & Architecture
* **Layered Architecture**: The system must maintain a strict separation of concerns; no class in the **Data Layer** shall contain logic related to the **Presentation Layer**.
* **ORM Usage**: 100% of CRUD (Create, Read, Update, Delete) operations on relational entities must be executed via the ORM, with zero manual SQL strings in the source code.
* **OOP Implementation**: The codebase must utilize Object-Oriented principles, maintaining a **maximum Coupling Between Objects (CBO) score of 14** per class.
* **UML Modeling**: Final implementation code must demonstrate a **90% or higher structural match** to the pre-approved UML Class and Sequence diagrams.
* **Data Persistence**: The system must successfully process and store a standard 5MB CSV file (approx. 50,000 rows) in under **5 seconds**.

---

## 2. Quality Attributes

### 2.1 Usability & Interface
* **Learnability**: A first-time user must be able to complete the "Create Budget" and "Log Expense" workflow in under **60 seconds** without referring to a manual.
* **Feedback Latency**: The visual warning for budget limit breaches must appear within **200ms** of the transaction being recorded.

### 2.2 Data Integrity & Accuracy
* **Validation Recovery**: 100% of malformed CSV rows (e.g., missing fields or incorrect types) must be caught during import, providing the user with a specific line-number error report.
* **Numerical Precision**: All financial calculations must utilize high-precision types (e.g., `BigDecimal` or `money`) to **four decimal places** to ensure zero rounding discrepancies.
* **Concurrency**: The system must accurately reflect category totals even when **5 simultaneous transactions** are posted to the same category.

### 2.3 Security & Privacy
* **Authentication**: Passwords must be hashed using **bcrypt** with a work factor of at least 10; plain text passwords must never be stored.
* **Session Termination**: Upon clicking "Logout," all session tokens must be invalidated on the server-side within **1 second**, preventing "Back" button access to data.
* **Data Isolation**: The system must return a **403 Forbidden** error if a user attempts to access a Resource ID belonging to a different User ID (preventing IDOR).

### 2.4 Maintainability & Reliability
* **Decoupling**: No business logic (e.g., tax calculations or limit logic) shall exist within the UI components; **100% of rules** must reside in the Business Layer.
* **Cyclomatic Complexity**: No method in the Business Layer shall have a cyclomatic complexity score higher than **10**.
* **Atomicity**: 100% of batch imports must follow **ACID properties**; if a single row in a 1,000-row import fails, the database must roll back to the state existing prior to the import.

---

## 3. Testing & Deployment
* **Code Coverage**: Unit tests must cover a minimum of **80% of the Business Layer** logic.
* **Build Success**: The CI/CD pipeline must execute the full test suite and report a "Pass/Fail" status in under **3 minutes** per commit.
* **Resource Constraints**: The Docker container must ensure the application runs on any host with **Docker v20.10+** using no more than **512MB of RAM** at idle.