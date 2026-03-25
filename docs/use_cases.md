# System Use Case Documentation

---

## UC-1: Import User and Spending Data
**ID and name:** UC-1: Import User and Spending Data 
**Primary actor:** User 
**Secondary actors:** File System 
**Description:** The user imports a CSV file containing account information and spending records to populate or update the system data.
**Trigger:** User initiates an "Initial Setup" or "Batch Update" via file import. 
**Preconditions:**
* **PRE-1:** The CSV file follows the system's required schema. 
**Postconditions:**
* **POST-1:** User accounts are created or updated in the database. 
* **POST-2:** Spending records are linked to the respective users. 

### Normal Flow
1. **User** selects the "Import" option. 
2. **System** prompts the user to upload a CSV file.
3. **User** provides the file path.
4. **System** validates the file format and data integrity.
5. **System** updates the internal database with the new records.
6. **System** confirms the number of records successfully imported.

### Exceptions
* **1.0.E1:** File format is invalid (e.g., missing columns). System displays an error message and terminates the import. 

---

## UC-2: Authenticate User
**ID and name:** UC-2: Authenticate User 
**Primary actor:** User 
**Secondary actors:** None 
**Description:** User logs into the system to access personal budget management tools. 
**Trigger:** User opens the application. 
**Preconditions:**
* **PRE-1:** User data has been previously imported (UC-1). 
**Postconditions:**
* **POST-1:** User is granted access to the dashboard. 

### Normal Flow
1. **System** displays the login screen. 
2. **User** enters credentials (username/password).
3. **System** validates credentials against the database.
4. **System** redirects user to the main spending overview.

### Alternative Flows
* **2.1:** User selects "Logout" from the dashboard; System terminates session and returns to login screen. 

### Exceptions
* **2.0.E1:** Invalid credentials; System displays "Authentication Failed" and prompts for re-entry. 

---

## UC-3: View and Export Monthly Spendings
**ID and name:** UC-3: View and Export Monthly Spendings 
**Primary actor:** User 
**Secondary actors:** File System 
**Description:** User views a report of expenses for a specific month and optionally exports it to a file. 
**Trigger:** User selects "View Monthly Spendings." 
**Preconditions:**
* **PRE-1:** User is logged in. 
**Postconditions:**
* **POST-1:** Data is displayed or a file is saved to the local machine. 

### Normal Flow
1. **User** selects a specific month and year. 
2. **System** retrieves and displays all expenses for that period.
3. **User** requests an "Export."
4. **System** prompts for format (CSV, XSL, or TXT).
5. **User** selects format.
6. **System** generates and downloads the file.

### Exceptions
* **3.0.E1:** No data found for the selected period; System informs the user and returns to the selection screen. 

---

## UC-4: Manage Personal Expenses
**ID and name:** UC-4: Manage Personal Expenses 
**Primary actor:** User 
**Secondary actors:** None 
**Description:** User adds, updates, or removes individual spending records. 
**Trigger:** User selects "Add," "Update," or "Remove" expense. 
**Preconditions:**
* **PRE-1:** User is logged in. 
**Postconditions:**
* **POST-1:** Expense record is modified in the database. 

### Normal Flow (Add Expense)
1. **User** selects "Add Expense." 
2. **System** prompts for date, value, and category.
3. **User** enters data and confirms.
4. **System** saves the record.
5. **System** checks if a category limit has been reached (Link to UC-6).

### Alternative Flows
* **4.1 Update:** User selects existing expense, modifies fields, and confirms. 
* **4.2 Remove:** User selects "Remove" on an expense; System deletes the record. 

### Exceptions
* **4.0.E1:** Negative value entered; System displays validation error. 

---

## UC-5: Categorize and Budget Expenses
**ID and name:** UC-5: Categorize and Budget Expenses 
**Primary actor:** User 
**Secondary actors:** None 
**Description:** User sets budget limits per category and views spending distribution. 
**Trigger:** User accesses "Categories Budgeting." 
**Preconditions:**
* **PRE-1:** User is logged in. 
**Postconditions:**
* **POST-1:** Budget limits are saved for the specified categories. 

### Normal Flow
1. **User** selects a category. 
2. **System** displays current spending vs. limit for that category.
3. **User** enters a new budget limit.
4. **System** updates the limit status.

### Alternative Flows
* **5.1 View Categorized Spending:** User requests to see spending over a specific duration per category. 

---

## UC-6: Monitor Budget Limits
**ID and name:** UC-6: Monitor Budget Limits 
**Primary actor:** User 
**Secondary actors:** None 
**Description:** The system monitors spending against set limits and issues warnings. 
**Trigger:** User adds or updates an expense (UC-4). 
**Preconditions:**
* **PRE-1:** Budget limits have been set (UC-5). 
**Postconditions:**
* **POST-1:** User is notified if a limit is exceeded. 

### Normal Flow
1. **System** calculates total spending for the category of the new/updated expense. 
2. **System** compares total to the set limit.
3. **If** total > limit, **System** displays "Limit Reached Warning."