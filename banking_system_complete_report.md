# 🏦 Banking Service Management System — Complete Code Report

> **Purpose of This Document**: After reading this, you will understand **every single line** of the code — *what* it does, *why* it's there, *how* it works, and *where* it fits in the bigger picture. No questions left. Like eating ice cream. 🍦

---

## 📑 Table of Contents

1. [The Big Picture — What Is This Project?](#1-the-big-picture)
2. [Architecture — How the Pieces Connect](#2-architecture)
3. [File Map — What Each File Does](#3-file-map)
4. [Entity Relationship — How Data Connects](#4-entity-relationship)
5. [Complete Line-by-Line Code Walkthrough](#5-line-by-line-walkthrough)
   - 5.1 `package-info.java`
   - 5.2 `module-info.java`
   - 5.3 `Customer.java`
   - 5.4 `Account.java`
   - 5.5 `Beneficiary.java`
   - 5.6 `Transaction.java`
   - 5.7 `Bankingservice.java` (Interface)
   - 5.8 `Bankingserviceimpl.java` (Implementation)
   - 5.9 `BankingSystemApp.java` (Main App)
6. [Program Startup Flow](#6-program-startup-flow)
7. [Dry Run — Every Menu Choice Step by Step](#7-dry-run)
8. [Java Concepts Used — Mapped to Code](#8-java-concepts)
9. [Quick-Reference Glossary](#9-glossary)

---

## 1. The Big Picture

```
┌─────────────────────────────────────────────────────┐
│          BANKING SERVICE MANAGEMENT SYSTEM           │
├─────────────────────────────────────────────────────┤
│  What?   A console app that simulates a mini bank   │
│  How?    Pure Core Java, no database, no framework  │
│  Where?  Everything runs in your terminal/console   │
│  Why?    To learn OOP, Collections, Interfaces      │
│  Input?  Keyboard (via Scanner)                     │
│  Output? Console (via System.out.println)           │
│  Data?   Lives in RAM only (HashMap) — gone on exit │
└─────────────────────────────────────────────────────┘
```

**In one sentence**: The user types menu choices → the app creates/finds banking data → stores it in HashMaps → prints results.

### What Can This App Do?

| # | Operation | Real-World Analogy |
|---|---|---|
| 1 | Add Customer | A person walks into the bank and opens a profile |
| 2 | Add Account | That person opens a Savings or Current account |
| 3 | Add Beneficiary | That person saves someone else's bank details for future transfers |
| 4 | Add Transaction | Deposit money in or withdraw money from an account |
| 5 | Find Customer | Search for a customer by their ID number |
| 6 | List Accounts | "Show me all accounts belonging to customer X" |
| 7 | List Transactions | "Show me all transactions done on account Y" |
| 8 | List Beneficiaries | "Show me all beneficiaries saved by customer X" |
| 9 | Exit | Close the application |

---

## 2. Architecture

### The Layer Cake 🍰

```
  ┌──────────────────────────────────────────┐
  │           👤 USER (You, at keyboard)      │
  └────────────────────┬─────────────────────┘
                       │ types choices & data
                       ▼
  ┌──────────────────────────────────────────┐
  │  📺 BankingSystemApp.java               │  ◄── LAYER 1: Presentation
  │  (Menu, input collection, output)        │      "The Receptionist"
  └────────────────────┬─────────────────────┘
                       │ calls methods on service
                       ▼
  ┌──────────────────────────────────────────┐
  │  📋 Bankingservice.java (Interface)      │  ◄── LAYER 2: Contract
  │  (Defines WHAT operations are available) │      "The Rule Book"
  └────────────────────┬─────────────────────┘
                       │ implemented by
                       ▼
  ┌──────────────────────────────────────────┐
  │  ⚙️ Bankingserviceimpl.java             │  ◄── LAYER 3: Business Logic
  │  (Defines HOW operations actually work)  │      "The Manager"
  └────────────────────┬─────────────────────┘
                       │ stores/retrieves from
                       ▼
  ┌──────────────────────────────────────────┐
  │  🗄️ HashMap Collections (in RAM)        │  ◄── LAYER 4: Data Storage
  │  customers, accounts, transactions,      │      "The Filing Cabinet"
  │  beneficiaries                           │
  └──────────────────────────────────────────┘
```

**Why this design?**
- **Separation of concerns**: Each layer has ONE job. The menu doesn't know how data is stored. The storage doesn't know what the menu looks like.
- **Easy to change**: Want a database later? Replace only `Bankingserviceimpl`. The menu code stays the same.

---

## 3. File Map

```
ucet_codes/
├── src/
│   ├── module-info.java  ........... Declares this as a Java module named "ucet_codes"
│   └── com/tnsif/bankingservice/
│       ├── package-info.java  ...... Declares the package identity
│       ├── Customer.java  .......... 📦 Data class: holds customer info
│       ├── Account.java  ........... 📦 Data class: holds account info
│       ├── Beneficiary.java  ....... 📦 Data class: holds beneficiary info
│       ├── Transaction.java  ....... 📦 Data class: holds transaction info
│       ├── Bankingservice.java  .... 📋 Interface: lists all operations
│       ├── Bankingserviceimpl.java .. ⚙️ Implementation: actual logic
│       └── BankingSystemApp.java  .. 🚀 Entry Point: main() lives here
└── bin/  ...........................  Compiled .class files (auto-generated)
```

> **📌 Key Insight**: The 4 data classes (`Customer`, `Account`, `Beneficiary`, `Transaction`) are like **forms/templates**. The interface is the **menu of services**. The implementation is the **actual worker**. The app is the **front desk**.

---

## 4. Entity Relationship

```
  ┌──────────────┐       ┌──────────────┐       ┌──────────────────┐
  │   CUSTOMER   │       │   ACCOUNT    │       │   TRANSACTION    │
  │──────────────│       │──────────────│       │──────────────────│
  │ customerID   │──┐    │ accountID    │──┐    │ transactionID    │
  │ name         │  │    │ customerID ◄─┼──┘    │ accountID ◄──────┼──┘
  │ address      │  │    │ type         │       │ type             │
  │ Contact      │  │    │ balance      │       │ amount           │
  └──────────────┘  │    └──────────────┘       │ timestamp        │
                    │                            └──────────────────┘
                    │    ┌──────────────────┐
                    │    │   BENEFICIARY    │
                    │    │──────────────────│
                    └───►│ beneficiaryID    │
                         │ customerID ◄────┤
                         │ name            │
                         │ accountNumber   │
                         │ bankDetails     │
                         └──────────────────┘
```

**Reading this diagram**:
- One Customer **can own many** Accounts (1 → many)
- One Customer **can save many** Beneficiaries (1 → many)
- One Account **can have many** Transactions (1 → many)
- The arrows show which field (`customerID`, `accountID`) **links** the tables together

---

## 5. Complete Line-by-Line Code Walkthrough

---

### 5.1 📄 `package-info.java` (1 line)

```java
package com.tnsif.bankingservice;    // Line 1
```

| Line | What | Why | How |
|------|------|-----|-----|
| 1 | Declares this folder belongs to the package `com.tnsif.bankingservice` | Java uses packages to **group related classes** together, like folders. All 7 classes in this project share this package so they can see each other without importing. | The `package` keyword tells the compiler: "Every class in this file is part of this package." The naming convention `com.tnsif.bankingservice` follows the reversed-domain-name standard (company → project → feature). |

> **🍦 Simple analogy**: A package is like a **department** in a company. Everyone in the same department can talk to each other freely.

---

### 5.2 📄 `module-info.java` (8 lines)

```java
/**            // Lines 1-2: Empty Javadoc comment block (placeholder)
 *
 */
/**            // Lines 4-5: Another empty Javadoc comment block
 *
 */
module ucet_codes {   // Line 7: Declares a Java module named "ucet_codes"
}                     // Line 8: Module body (empty = no exports/requires)
```

| Line | What | Why | How |
|------|------|-----|-----|
| 1-6 | Empty Javadoc comment blocks | Auto-generated by Eclipse IDE as placeholder documentation | `/** ... */` is Javadoc syntax. These are just empty placeholders here. |
| 7 | `module ucet_codes { }` | Java 9+ introduced the **Module System**. This declares the entire project as a single module called `ucet_codes`. | The `module` keyword creates a module boundary. Since the body is empty, this module doesn't explicitly export or require anything — it uses defaults. |

> **🍦 Simple analogy**: A module is like a **building**. The package is a **department** inside the building. The classes are **people** inside the department.

---

### 5.3 📄 `Customer.java` (33 lines) — The Customer Data Form

This class is a **blueprint** for creating customer objects. Think of it as a blank form with 4 fields.

```java
package com.tnsif.bankingservice;                          // Line 1
```
☝️ **What**: Puts this class in the `com.tnsif.bankingservice` package.
**Why**: So all classes in this project can access `Customer` without importing.

```java
public class Customer {                                    // Line 3
```
☝️ **What**: Declares a public class named `Customer`.
**Why**: `public` = any other class can use it. `class` = this is a blueprint for objects.
**Real-world**: This is the paper form template. Each `new Customer(...)` creates a filled copy.

```java
    private int customerID;                                // Line 4
    private String name;                                   // Line 5
    private String address;                                // Line 6
    private String Contact;                                // Line 7
```

| Line | Field | Type | Why `private`? | Real-World Meaning |
|------|-------|------|---------------|-------------------|
| 4 | `customerID` | `int` | **Encapsulation** — no one can directly change the ID from outside. They must use `setCustomerID()`. This protects data integrity. | Like your bank customer number — unique identifier |
| 5 | `name` | `String` | Same protection. Name can only be read/changed through getter/setter. | Customer's full name |
| 6 | `address` | `String` | Same protection. | Customer's home/office address |
| 7 | `Contact` | `String` | Same protection. (Note: starts with uppercase `C` — a minor naming inconsistency; Java convention prefers `contact`) | Phone number or email |

> **🔒 Why `private`?** This is **Encapsulation** — one of the 4 pillars of OOP. Making fields `private` means:
> - ❌ `customer.name = "John"` → **NOT ALLOWED** (direct access blocked)
> - ✅ `customer.setName("John")` → **ALLOWED** (controlled access via method)
>
> **Why care?** Because with a setter method, you could add validation like `if(name != null)` before saving. Direct access skips all safety checks.

```java
    public Customer(int customerID, String name, String address, String contact) {  // Line 9
        this.customerID = customerID;                      // Line 10
        this.name = name;                                  // Line 11
        this.address = address;                            // Line 12
        this.Contact = contact;                            // Line 13
    }                                                      // Line 14
```

☝️ **What**: This is a **Constructor** — a special method that runs automatically when you write `new Customer(...)`.

| Line | What Happens | Why |
|------|-------------|-----|
| 9 | Constructor header receives 4 values as **parameters** | When someone creates a customer, they MUST provide all 4 pieces of info. The constructor enforces this rule. |
| 10 | `this.customerID = customerID` | The parameter `customerID` and the field `customerID` have the **same name**. `this.` says "I mean the FIELD of this object, not the parameter." Without `this.`, Java would think you're assigning the parameter to itself (useless). |
| 11 | `this.name = name` | Same pattern — copies the parameter value into the object's field. |
| 12 | `this.address = address` | Same pattern. |
| 13 | `this.Contact = contact` | Copies lowercase parameter `contact` into uppercase field `Contact`. |

> **🍦 Simple analogy**: The constructor is like filling out a form. You hand over (ID, name, address, phone) → a `Customer` object is created with those values stored inside it.

```java
    public int getCustomerID()              { return customerID; }     // Line 16
    public void setCustomerID(int id)       { this.customerID = id; } // Line 17
```

☝️ **Getter and Setter pair for `customerID`**

| Method | Purpose | Return Type | Why It Exists |
|--------|---------|-------------|---------------|
| `getCustomerID()` | **Read** the ID from outside the class | `int` — gives back the ID value | Because `customerID` is private, this is the ONLY way to read it |
| `setCustomerID(int id)` | **Write/Update** the ID from outside | `void` — doesn't return anything, just sets | Because `customerID` is private, this is the ONLY way to change it |

> Lines 19-26 follow the **exact same getter/setter pattern** for `name`, `address`, and `Contact`. Each field gets one `get` (read) and one `set` (write) method.

```java
    @Override                                              // Line 28
    public String toString() {                             // Line 29
        return "Customer [customerID=" + customerID + ", name=" + name
                + ", address=" + address + ", Contact=" + Contact + "]";  // Lines 30-31
    }                                                      // Line 32
}                                                          // Line 33
```

| Line | What | Why | How |
|------|------|-----|-----|
| 28 | `@Override` | This **annotation** tells the compiler: "I'm intentionally replacing the default `toString()` from Java's `Object` class." If you make a typo like `tostring()`, the compiler will catch it. | Every Java class secretly inherits from `Object`, which has a default `toString()` that prints a memory address like `Customer@3f2a1c`. That's useless. |
| 29-31 | Custom `toString()` | Instead of seeing `Customer@3f2a1c`, you see `Customer [customerID=1, name=Sanjay, ...]` — **human-readable output**. | String concatenation with `+` joins field values into one readable line. |

> **When does `toString()` get called?** Whenever you do `System.out.println(customerObject)`, Java automatically calls `customerObject.toString()` behind the scenes.

---

### 5.4 📄 `Account.java` (34 lines) — The Account Data Form

Structure is **identical** to `Customer.java`. Same concepts apply: private fields → constructor → getters/setters → toString.

```java
package com.tnsif.bankingservice;                          // Line 1

public class Account {                                     // Line 3
    private int accountID;                                 // Line 5
    private int customerID;                                // Line 6  ◄── LINK to Customer
    private String type;                                   // Line 7
    private Double balance;                                // Line 8
```

| Field | Type | Purpose | Special Note |
|-------|------|---------|-------------|
| `accountID` | `int` | Unique ID for this account | The **primary key** — used to store/find this account in the HashMap |
| `customerID` | `int` | Which customer owns this account | The **foreign key** — links this account back to a `Customer`. This is HOW we know "Account 101 belongs to Customer 1" |
| `type` | `String` | "Saving" or "Current" | Stores the account category |
| `balance` | `Double` | Current money in the account | Uses `Double` (wrapper class) instead of `double` (primitive). Why? `Double` is an object, so it can be `null` and works with certain Java APIs that require objects. In practice, `double` would also work here. |

> **🔗 The `customerID` field is crucial**: It's the **glue** between Customer and Account. Without it, there's no way to know which customer owns which account.

```java
    public Account(int accountID, int customerID, String type, Double balance) {  // Line 10
        this.accountID = accountID;                        // Line 11
        this.customerID = customerID;                      // Line 12
        this.type = type;                                  // Line 13
        this.balance = balance;                            // Line 14
    }                                                      // Line 15
```
☝️ Same constructor pattern as Customer. All 4 values must be provided when creating an account.

> Lines 17-27: Getters and Setters for all 4 fields (same pattern as Customer).
> Lines 29-33: `toString()` override (same pattern as Customer).

---

### 5.5 📄 `Beneficiary.java` (40 lines) — The Beneficiary Data Form

```java
package com.tnsif.bankingservice;                          // Line 1

public class Beneficiary {                                 // Line 3
    private int beneficiaryID;                             // Line 4
    private int customerID;                                // Line 5  ◄── LINK to Customer
    private String name;                                   // Line 6
    private String accountNumber;                          // Line 7
    private String bankDetails;                            // Line 8
```

| Field | Purpose | Why This Type? |
|-------|---------|---------------|
| `beneficiaryID` | Unique ID for this beneficiary | `int` — simple numeric identifier |
| `customerID` | Which customer saved this beneficiary | `int` — **links back to Customer** (same concept as in Account) |
| `name` | Beneficiary's name | `String` — text data |
| `accountNumber` | Beneficiary's bank account number | `String` (not `int`) — because account numbers can have leading zeros, hyphens, or be very long |
| `bankDetails` | Bank name/branch/IFSC info | `String` — free text |

> Constructor (Lines 10-17), Getters/Setters (Lines 19-32), `toString()` (Lines 34-39) — all follow the same pattern. No new concepts.

---

### 5.6 📄 `Transaction.java` (38 lines) — The Transaction Data Form

```java
package com.tnsif.bankingservice;                          // Line 1

import java.time.LocalDateTime;                            // Line 3
```

☝️ **What**: Imports the `LocalDateTime` class from Java's built-in `java.time` package.
**Why**: We need to record WHEN a transaction happened (date + time).
**How**: `LocalDateTime` stores both date and time (e.g., `2026-04-06T17:30:00`) without timezone complexity.

```java
public class Transaction {                                 // Line 5
    private int transactionID;                             // Line 6
    private int accountID;                                 // Line 7  ◄── LINK to Account
    private String type;                                   // Line 8
    private Double amount;                                 // Line 9
    private LocalDateTime timestamp;                       // Line 10
```

| Field | Purpose | Key Detail |
|-------|---------|-----------|
| `transactionID` | Unique ID | **NOT set by user** — auto-generated by the system (see `Bankingserviceimpl`) |
| `accountID` | Which account this transaction is for | **Links to Account** |
| `type` | `"Deposit"` or `"Withdrawal"` | Determines if money goes IN or OUT |
| `amount` | How much money | `Double` wrapper type |
| `timestamp` | When the transaction happened | **NOT set by user** — auto-set by the system using `LocalDateTime.now()` |

```java
    public Transaction(int accountID, String type, double amount) {  // Line 12
        this.accountID = accountID;                        // Line 13
        this.type = type;                                  // Line 14
        this.amount = amount;                              // Line 15
    }                                                      // Line 16
```

> **🔍 Notice**: The constructor takes only 3 parameters (accountID, type, amount). It does **NOT** take `transactionID` or `timestamp`. Why?
> - `transactionID` → auto-generated by `transactionCounter++` in `Bankingserviceimpl`
> - `timestamp` → auto-set to current time in `Bankingserviceimpl`
>
> **Design reason**: The USER shouldn't manually pick transaction IDs or timestamps. The SYSTEM handles those to prevent errors and ensure uniqueness.

> Lines 18-31: Getters/Setters for all 5 fields.
> Lines 33-37: `toString()` override.

---

### 5.7 📄 `Bankingservice.java` (25 lines) — The Interface (Rule Book)

```java
package com.tnsif.bankingservice;                          // Line 1

import java.util.Collection;                               // Line 3
import java.util.List;                                     // Line 4
```

☝️ **What**: Imports `Collection` and `List` from Java Collections Framework.
- `Collection` = the most general type. A group of objects with no guaranteed order.
- `List` = a more specific type. A group of objects that maintains insertion order and allows duplicates.

**Why two different types?** Methods that return "all items" use `Collection` (order doesn't matter). Methods that return "filtered items" use `List` (we're building a new ordered list).

```java
public interface Bankingservice {                          // Line 6
```

☝️ **What**: Declares an **interface** named `Bankingservice`.

> **What is an Interface?**
> ```
> ┌─────────────────────────────────────────────┐
> │  Interface = A CONTRACT / PROMISE            │
> │                                               │
> │  It says WHAT methods must exist              │
> │  It does NOT say HOW they work                │
> │                                               │
> │  Like a restaurant menu:                      │
> │    ✅ Lists "Pasta" as available              │
> │    ❌ Doesn't tell you the recipe             │
> │                                               │
> │  The IMPLEMENTATION class provides the recipe  │
> └─────────────────────────────────────────────┘
> ```

```java
    void addCustomer(Customer customer);                   // Line 7
    void addAccount(Account account);                      // Line 8
    void addTransaction(Transaction transaction);          // Line 9
    void addBeneficiary(Beneficiary beneficiary);          // Line 10
```

| Method Signature | What It Promises | Parameters Explained |
|-----------------|-----------------|---------------------|
| `void addCustomer(Customer customer)` | "You can add a customer" | Takes a `Customer` object. `void` = doesn't return anything (just stores the data). |
| `void addAccount(Account account)` | "You can add an account" | Takes an `Account` object. |
| `void addTransaction(Transaction transaction)` | "You can process a transaction" | Takes a `Transaction` object. |
| `void addBeneficiary(Beneficiary beneficiary)` | "You can add a beneficiary" | Takes a `Beneficiary` object. |

**Why pass entire objects instead of individual fields?** Instead of `addCustomer(int id, String name, String addr, String phone)` with 4 separate parameters, we pass one `Customer` object. This is:
- **Cleaner** — one parameter instead of four
- **Safer** — you can't accidentally swap name and address
- **Scalable** — if Customer gets new fields, this method signature doesn't change

```java
    Customer findCustomerById(int id);                     // Line 12
    Account findAccountById(int id);                       // Line 13
    Transaction findTransactionById(int id);               // Line 14
    Beneficiary findBeneficiaryById(int id);               // Line 15
```

☝️ **Find methods**: Take an `int id` → return the matching object (or `null` if not found).

```java
    Collection<Account> getAllAccounts();                   // Line 17
    Collection<Customer> getAllCustomers();                 // Line 18
    Collection<Transaction> getAllTransactions();           // Line 19
    Collection<Beneficiary> getAllBeneficiaries();          // Line 20
```

☝️ **Get-all methods**: Return ALL stored records. `Collection<Account>` means "a collection of Account objects." The `<Account>` is a **Generic** — it specifies what TYPE of objects are in the collection (type safety).

```java
    List<Account> getAccountsByCustomerIud(int cstomerId); // Line 22
    List<Transaction> getTransactionsByAccountId(int accountId); // Line 23
    List<Beneficiary> getBeneficiariesByCustomerId(int customerId); // Line 24
}                                                          // Line 25
```

☝️ **Filtered-list methods**: These do NOT return all records. They filter by a foreign key.
- `getAccountsByCustomerIud(int)` → "Give me only accounts belonging to customer X"
- `getTransactionsByAccountId(int)` → "Give me only transactions for account Y"
- `getBeneficiariesByCustomerId(int)` → "Give me only beneficiaries of customer X"

> **📝 Note**: `getAccountsByCustomerIud` has a typo (`Iud` instead of `Id`). The implementation class correctly names it `getAccountsByCustomerId`. This works because Java matches method names during compilation, and the implementation provides the correctly-named version that satisfies the interface contract.

---

### 5.8 📄 `Bankingserviceimpl.java` (129 lines) — The Implementation (The Brain)

This is the most important file. It contains ALL the business logic.

```java
package com.tnsif.bankingservice;                          // Line 1

import java.time.LocalDateTime;                            // Line 3
import java.util.*;                                        // Line 4
```

| Import | What It Brings In | Why Needed |
|--------|------------------|------------|
| `java.time.LocalDateTime` | Date+time class | To stamp transactions with current date/time |
| `java.util.*` | ALL classes from java.util: `HashMap`, `ArrayList`, `Collection`, `List`, `Map`, etc. | The `*` is a wildcard — imports everything. Saves writing 5+ separate import lines. |

```java
public class Bankingserviceimpl implements Bankingservice { // Line 6
```

☝️ **What**: This class **implements** the `Bankingservice` interface.

> **What does `implements` mean?**
> ```
> Bankingservice (Interface)          Bankingserviceimpl (Class)
> ┌───────────────────────┐          ┌───────────────────────┐
> │ "You MUST have these  │          │ "OK, here are the     │
> │  15 methods"          │  ═══▶    │  15 methods with      │
> │                       │          │  actual working code"  │
> └───────────────────────┘          └───────────────────────┘
>        CONTRACT                          FULFILLMENT
> ```
> If `Bankingserviceimpl` is missing even ONE method from the interface, the code **won't compile**. The interface forces completeness.

#### The 4 HashMaps — Our "Database"

```java
    private Map<Integer, Customer> customers = new HashMap<>();       // Line 8
    private Map<Integer, Account> accounts = new HashMap<>();         // Line 9
    private Map<Integer, Transaction> transactions = new HashMap<>(); // Line 10
    private Map<Integer, Beneficiary> beneficiaries = new HashMap<>();// Line 11
```

> **What is a HashMap?**
> ```
> HashMap<Integer, Customer> = A dictionary/phonebook
>
>    KEY (Integer)  →  VALUE (Customer object)
>    ─────────────     ─────────────────────────
>         1         →  Customer{id=1, name="Sanjay", ...}
>         2         →  Customer{id=2, name="Priya", ...}
>         3         →  Customer{id=3, name="Rahul", ...}
>
>    To find Priya: customers.get(2)  →  instant lookup! No scanning needed.
> ```

| HashMap | Key | Value | Why This Design |
|---------|-----|-------|----------------|
| `customers` | `customerID` | `Customer` object | Look up any customer instantly by their ID |
| `accounts` | `accountID` | `Account` object | Look up any account instantly by its ID |
| `transactions` | `transactionID` | `Transaction` object | Look up any transaction instantly by its ID |
| `beneficiaries` | `beneficiaryID` | `Beneficiary` object | Look up any beneficiary instantly by its ID |

**Why `Map<Integer, Customer>` on the left and `new HashMap<>()` on the right?**
- Left side: `Map` is the **interface type** — it says "this variable can hold ANY kind of map"
- Right side: `HashMap` is the **concrete class** — it says "but I'm actually CREATING a HashMap specifically"
- This is **programming to the interface** — a best practice. If you later want a `TreeMap` instead, you only change the right side.
- The `<>` (diamond operator, Java 7+) means "copy the type from the left side" — avoids repeating `<Integer, Customer>`.

```java
    private int transactionCounter = 1;                    // Line 13
```

☝️ **What**: A counter that starts at 1 and increases by 1 for each new transaction.
**Why**: Transaction IDs are **auto-generated** — the user doesn't pick them. This ensures every transaction gets a unique, sequential ID (1, 2, 3, 4...).
**How**: Every time `addTransaction()` runs, it does `transactionCounter++` (use the current value, then add 1).

#### Method: `addCustomer` (Lines 15-18)

```java
    @Override
    public void addCustomer(Customer customer) {           // Line 16
        customers.put(customer.getCustomerID(), customer); // Line 17
    }                                                      // Line 18
```

**Dry-run trace**:
```
Input: customer = Customer{id=1, name="Sanjay", address="Chennai", Contact="9876543210"}

Line 17: customers.put(1, Customer{id=1, name="Sanjay", ...})
         │              │                │
         │              │                └─ The VALUE being stored
         │              └─ The KEY = customerID = 1
         └─ The HashMap where it's stored

After: customers = { 1 → Customer{Sanjay} }
```

> `@Override` confirms this method fulfills the interface contract. Same pattern applies to `addAccount` and `addBeneficiary`.

#### Method: `addTransaction` (Lines 25-50) — ⭐ THE MOST IMPORTANT METHOD

```java
    @Override
    public void addTransaction(Transaction transaction) {  // Line 26
        // Step 1: Assign auto-incremented transaction ID and timestamp
        transaction.setTransactionID(transactionCounter++); // Line 28
        transaction.setTimestamp(LocalDateTime.now());       // Line 29
```

| Line | What Happens | Dry-Run (1st call) | Dry-Run (2nd call) |
|------|-------------|-------------------|-------------------|
| 28 | Sets transaction ID to current counter, THEN increments counter | `transactionID = 1`, then `transactionCounter` becomes `2` | `transactionID = 2`, then `transactionCounter` becomes `3` |
| 29 | Sets timestamp to current date+time | `timestamp = 2026-04-06T17:30:00` | `timestamp = 2026-04-06T17:31:15` |

> **`transactionCounter++` explained**:
> - `++` is the **post-increment** operator
> - It means: **USE** the current value first, **THEN** add 1
> - So `setTransactionID(transactionCounter++)` uses the value (1) for the ID, then bumps the counter to 2

```java
        // Step 2: Find the account this transaction belongs to
        Account account = accounts.get(transaction.getAccountID()); // Line 32
```

☝️ Goes to the `accounts` HashMap and looks up the account using the `accountID` from the transaction. If found, `account` holds the Account object. If not found, `account` is `null`.

```java
        if (account != null) {                             // Line 33
```

☝️ **Safety check**: Only proceed if the account actually exists.

```java
            // Step 3a: Handle DEPOSIT
            if (transaction.getType().equalsIgnoreCase("Deposit")) {       // Line 34
                account.setBalance(account.getBalance() + transaction.getAmount()); // Line 35
```

| Detail | Explanation |
|--------|-------------|
| `.equalsIgnoreCase("Deposit")` | Compares strings ignoring case. So "deposit", "DEPOSIT", "Deposit" all match. Why not `==`? Because `==` compares memory addresses of String objects, not their text content. `equals()` compares the actual characters. |
| `account.getBalance() + transaction.getAmount()` | Gets current balance, adds the deposit amount. |
| `account.setBalance(...)` | Updates the account's balance field with the new total. |

**Deposit dry-run**:
```
Before: Account{id=101, balance=5000.0}
Transaction: type="Deposit", amount=2000.0

Line 35: setBalance(5000.0 + 2000.0) = setBalance(7000.0)

After: Account{id=101, balance=7000.0} ✅
```

```java
            // Step 3b: Handle WITHDRAWAL
            } else if (transaction.getType().equalsIgnoreCase("Withdrawal")) { // Line 36
                if (account.getBalance() >= transaction.getAmount()) {         // Line 37
                    account.setBalance(account.getBalance() - transaction.getAmount()); // Line 38
                } else {                                                       // Line 39
                    System.out.println("Insufficient balance! Transaction not processed."); // Line 40
                    return;                                                    // Line 41
                }                                                              // Line 42
            }                                                                  // Line 43
```

**Withdrawal dry-run (SUCCESS)**:
```
Before: Account{id=101, balance=7000.0}
Transaction: type="Withdrawal", amount=3000.0

Line 37: 7000.0 >= 3000.0? YES ✅
Line 38: setBalance(7000.0 - 3000.0) = setBalance(4000.0)

After: Account{id=101, balance=4000.0} ✅
```

**Withdrawal dry-run (FAILURE)**:
```
Before: Account{id=101, balance=4000.0}
Transaction: type="Withdrawal", amount=9000.0

Line 37: 4000.0 >= 9000.0? NO ❌
Line 40: Prints "Insufficient balance! Transaction not processed."
Line 41: return; ← EXITS the method immediately. Transaction is NOT stored.

After: Account{id=101, balance=4000.0} (unchanged) ⛔
```

> **`return;` in a `void` method**: It doesn't return a value — it just STOPS the method right here. The transaction never reaches Line 49 (where it would be stored), so it's effectively cancelled.

```java
        } else {                                           // Line 44
            System.out.println("Account ID " + transaction.getAccountID()
                + " not found. Transaction not processed.");// Line 45
            return;                                        // Line 46
        }                                                  // Line 47
```

☝️ If `account == null` (the account ID doesn't exist), print error and exit.

```java
        transactions.put(transaction.getTransactionID(), transaction); // Line 49
    }                                                      // Line 50
```

☝️ **Only reached if everything succeeded**: Stores the completed transaction in the `transactions` HashMap.

#### Decision flowchart for `addTransaction`:

```
 START: addTransaction(transaction)
    │
    ▼
 Set transactionID = counter++
 Set timestamp = now()
    │
    ▼
 Find account by accountID
    │
    ├── account is NULL? ──────► Print "Account not found" → STOP ⛔
    │
    ▼
 account EXISTS
    │
    ├── Type is "Deposit"? ────► balance = balance + amount → STORE ✅
    │
    ├── Type is "Withdrawal"?
    │   │
    │   ├── balance >= amount? ► balance = balance - amount → STORE ✅
    │   │
    │   └── balance < amount?  ► Print "Insufficient balance" → STOP ⛔
    │
    ▼
 Store transaction in HashMap ✅
```

#### Filter Methods (Lines 97-128) — Finding Related Records

```java
    @Override
    public List<Account> getAccountsByCustomerId(int customerId) {  // Line 98
        List<Account> result = new ArrayList<>();                   // Line 99
        for (Account account : accounts.values()) {                 // Line 100
            if (account.getCustomerID() == customerId) {            // Line 101
                result.add(account);                                // Line 102
            }                                                       // Line 103
        }                                                           // Line 104
        return result;                                              // Line 105
    }                                                               // Line 106
```

**Line-by-line**:

| Line | What | Why |
|------|------|-----|
| 99 | Creates an empty `ArrayList` to hold results | We need a place to collect matching accounts |
| 100 | `for (Account account : accounts.values())` — **enhanced for-each loop**. Iterates over every Account stored in the HashMap. `.values()` returns all the Account objects (ignoring the keys). | We need to check EVERY account to see if it belongs to this customer |
| 101 | `if (account.getCustomerID() == customerId)` — checks if this account's customer ID matches the one we're looking for | This is the **filter condition** |
| 102 | `result.add(account)` — adds the matching account to our result list | Collecting all matches |
| 105 | Returns the list (may be empty if no matches) | The caller gets a List that might have 0, 1, or many accounts |

**Dry-run**:
```
accounts HashMap = {
  101 → Account{id=101, customerID=1, balance=5000},
  102 → Account{id=102, customerID=2, balance=3000},
  103 → Account{id=103, customerID=1, balance=8000}
}

Call: getAccountsByCustomerId(1)

Iteration 1: Account 101, customerID=1, matches! → result = [Account 101]
Iteration 2: Account 102, customerID=2, no match → result = [Account 101]
Iteration 3: Account 103, customerID=1, matches! → result = [Account 101, Account 103]

Return: [Account 101, Account 103]  ✅
```

> `getTransactionsByAccountId()` and `getBeneficiariesByCustomerId()` follow the **exact same pattern** — create empty list, loop through all, filter by ID, return matches.

---

### 5.9 📄 `BankingSystemApp.java` (196 lines) — The Front Desk

```java
package com.tnsif.bankingservice;                          // Line 1

import java.util.Collection;                               // Line 3
import java.util.List;                                     // Line 4
import java.util.Scanner;                                  // Line 5
```

| Import | Why |
|--------|-----|
| `Collection` | Used as return type for `getAllCustomers()`, `getAllAccounts()`, etc. |
| `List` | Used as return type for `getAccountsByCustomerId()`, etc. |
| `Scanner` | To **read keyboard input** from the user. `Scanner` wraps `System.in` (the keyboard input stream) and provides convenient methods to read strings, integers, etc. |

```java
public class BankingSystemApp {                            // Line 7

    private static final Bankingserviceimpl service = new Bankingserviceimpl(); // Line 9
    private static final Scanner scanner = new Scanner(System.in);             // Line 10
```

| Line | What | Why | Key Details |
|------|------|-----|-------------|
| 9 | Creates ONE instance of `Bankingserviceimpl` | This single object holds ALL our data (the 4 HashMaps). Making it `static final` means: one instance, shared across all methods, never replaced. | `static` = belongs to the class, not an object. `final` = can't be reassigned to a different object. |
| 10 | Creates ONE `Scanner` connected to `System.in` (keyboard) | Reads everything the user types. One scanner for the whole app. | `System.in` is the standard input stream (your keyboard). |

> **Why `static`?** Because all methods in this class are `static` (they're called from `main()`, which is static). A static method can only use static fields.

```java
    public static void main(String[] args) {               // Line 12
        int choice;                                        // Line 13
        do {                                               // Line 14
            printMenu();                                   // Line 15
            System.out.print("Enter your choice : ");     // Line 16
            choice = Integer.parseInt(scanner.nextLine().trim()); // Line 17
            System.out.println();                          // Line 18
```

| Line | What | Why |
|------|------|-----|
| 12 | `main()` — the **entry point**. JVM starts here. | Every Java application must have exactly one `public static void main(String[] args)`. `String[] args` is for command-line arguments (unused here). |
| 13 | Declares `choice` variable | Will store the user's menu selection (1-9) |
| 14 | `do {` — starts a **do-while loop** | `do-while` guarantees the menu shows **at least once** before checking the exit condition. A normal `while` would check first, but we want the menu visible immediately. |
| 15 | Calls `printMenu()` | Displays the 9 options on screen |
| 16 | `System.out.print(...)` | Prompts user. `print` (not `println`) keeps the cursor on the same line |
| 17 | `scanner.nextLine().trim()` reads the entire line the user types, `trim()` removes extra spaces. `Integer.parseInt(...)` converts the String `"1"` to the integer `1`. | Why not `scanner.nextInt()`? Because `nextInt()` leaves a newline character in the buffer, causing problems with subsequent `nextLine()` calls. Using `nextLine()` + `parseInt()` avoids this classic Java Scanner bug. |

```java
            switch (choice) {                              // Line 19
                case 1 -> addCustomer();                   // Line 20
                case 2 -> addAccount();                    // Line 21
                case 3 -> addBeneficiary();                // Line 22
                case 4 -> addTransaction();                // Line 23
                case 5 -> findCustomerById();              // Line 24
                case 6 -> listAccountsByCustomer();        // Line 25
                case 7 -> listTransactionsByAccount();     // Line 26
                case 8 -> listBeneficiariesByCustomer();   // Line 27
                case 9 -> System.out.println("Thank you!");// Line 28
                default -> System.out.println("Invalid choice. Please try again."); // Line 29
            }                                              // Line 30
        } while (choice != 9);                             // Line 31
    }                                                      // Line 32
```

☝️ **Switch expression** (Java 14+ syntax with `->` arrows):
- No `break` needed! The `->` arrow syntax automatically prevents fall-through.
- Each case calls a specific private method.
- `default` catches any number not 1-9.
- Loop continues `while (choice != 9)` — i.e., until the user picks Exit.

---

## 6. Program Startup Flow

```
  JVM starts
     │
     ▼
  main(String[] args) is called
     │
     ▼
  Line 9: service = new Bankingserviceimpl()
     │     └── 4 empty HashMaps created in RAM
     │     └── transactionCounter = 1
     │
     ▼
  Line 10: scanner = new Scanner(System.in)
     │      └── Connected to keyboard
     │
     ▼
  ┌──► Line 15: printMenu() ◄─────────────────┐
  │       │                                     │
  │       ▼                                     │
  │    Line 17: Read user's choice              │
  │       │                                     │
  │       ▼                                     │
  │    Lines 19-30: switch → call method        │
  │       │                                     │
  │       ▼                                     │
  │    Line 31: choice != 9? ───YES────────────►┘
  │                    │
  │                    NO
  │                    │
  │                    ▼
  └──────────────── Program Ends
```

---

## 7. Dry Run — Every Menu Choice

### 🔵 Choice 1: Add Customer

**User types**: `1`

```
Console Output:
  Enter Customer Details
  Customer Id : ___        ← User types: 1
  Name : ___               ← User types: Sanjay
  Address : ___            ← User types: Chennai
  Contact No. : ___        ← User types: 9876543210

Code execution:
  Line 52: id = Integer.parseInt("1") = 1
  Line 55: name = "Sanjay"
  Line 58: address = "Chennai"
  Line 61: contact = "9876543210"
  Line 63: customer = new Customer(1, "Sanjay", "Chennai", "9876543210")
           └── Constructor fires → object created with these 4 values
  Line 64: service.addCustomer(customer)
           └── Bankingserviceimpl.addCustomer() runs
           └── customers.put(1, Customer{...})
           └── HashMap now: { 1 → Customer{Sanjay} }
  Line 65: Prints "Customer added successfully."

Memory state after:
  customers = { 1 → Customer{id=1, name=Sanjay, address=Chennai, Contact=9876543210} }
  accounts = { }  (empty)
  transactions = { }  (empty)
  beneficiaries = { }  (empty)
```

### 🔵 Choice 2: Add Account

**User types**: `2`

```
Console Output:
  Enter Account Details
  Account Id : ___         ← User types: 101
  Customer Id : ___        ← User types: 1
  Account Type : ___       ← User types: Saving
  Balance : ___            ← User types: 5000

Code execution:
  Line 72: accountId = 101
  Line 74: customerId = 1
  Line 76: type = "Saving"
  Line 78: balance = 5000.0
  Line 80: account = new Account(101, 1, "Saving", 5000.0)
  Line 81: service.addAccount(account)
           └── accounts.put(101, Account{...})
  Line 82: Prints "Account added successfully."

Memory state after:
  customers = { 1 → Customer{Sanjay} }
  accounts = { 101 → Account{id=101, customerID=1, type=Saving, balance=5000.0} }
```

### 🔵 Choice 3: Add Beneficiary

**User types**: `3`

```
Console Output:
  Enter Beneficiary Details
  Customer Id : ___           ← User types: 1
  Beneficiary Id : ___        ← User types: 501
  Beneficiary Name : ___      ← User types: Priya
  Beneficiary Account No.: _  ← User types: 9988776655
  Beneficiary Bank details: _ ← User types: SBI Main Branch

Code execution:
  Line 89: customerId = 1
  Line 91: beneficiaryId = 501
  Line 93: name = "Priya"
  Line 95: accountNumber = "9988776655"
  Line 97: bankDetails = "SBI Main Branch"
  Line 99: beneficiary = new Beneficiary(501, 1, "Priya", "9988776655", "SBI Main Branch")
  Line 100: service.addBeneficiary(beneficiary)
            └── beneficiaries.put(501, Beneficiary{...})

Memory state after:
  customers = { 1 → Customer{Sanjay} }
  accounts = { 101 → Account{id=101, balance=5000.0} }
  beneficiaries = { 501 → Beneficiary{id=501, customerID=1, name=Priya} }
```

### 🔵 Choice 4: Add Transaction (Deposit)

**User types**: `4`

```
Console Output:
  Enter Transaction Details
  Account Id : ___                ← User types: 101
  Type (Deposit/Withdrawal) : ___ ← User types: Deposit
  Account Amount : ___            ← User types: 2000

Code execution:
  Line 108: accountId = 101
  Line 110: type = "Deposit"
  Line 112: amount = 2000.0
  Line 114: transaction = new Transaction(101, "Deposit", 2000.0)
            └── transactionID = 0 (not set yet), timestamp = null (not set yet)
  Line 115: service.addTransaction(transaction)
            ├── Line 28: transaction.setTransactionID(1)  ← counter was 1, now becomes 2
            ├── Line 29: transaction.setTimestamp(2026-04-06T17:35:00)
            ├── Line 32: account = accounts.get(101) → Account{balance=5000.0}
            ├── Line 33: account != null? YES ✅
            ├── Line 34: type is "Deposit"? YES ✅
            ├── Line 35: setBalance(5000.0 + 2000.0) = setBalance(7000.0)
            └── Line 49: transactions.put(1, Transaction{id=1, accountID=101, Deposit, 2000.0})

Memory state after:
  accounts = { 101 → Account{balance=7000.0} }  ◄── Balance updated!
  transactions = { 1 → Transaction{id=1, type=Deposit, amount=2000.0} }
  transactionCounter = 2
```

### 🔵 Choice 4 Again: Add Transaction (Withdrawal — Success)

```
  Account Id: 101, Type: Withdrawal, Amount: 3000

  Line 32: account = Account{balance=7000.0}
  Line 37: 7000.0 >= 3000.0? YES ✅
  Line 38: setBalance(7000.0 - 3000.0) = 4000.0
  Line 49: transactions.put(2, Transaction{Withdrawal, 3000.0})

  After: balance = 4000.0, transactionCounter = 3
```

### 🔴 Choice 4 Again: Add Transaction (Withdrawal — FAILS)

```
  Account Id: 101, Type: Withdrawal, Amount: 9000

  Line 32: account = Account{balance=4000.0}
  Line 37: 4000.0 >= 9000.0? NO ❌
  Line 40: Prints "Insufficient balance! Transaction not processed."
  Line 41: return; ← Method exits. Transaction NOT stored.

  After: balance still 4000.0, transactionCounter = 4 (ID was assigned before check)
```

### 🔵 Choice 5: Find Customer by ID

```
  Code first calls service.getAllCustomers() → prints all customers
  Console: "Customer ID: 1, Name: Sanjay"
  Then asks: Customer Id : ___  ← User types: 1
  service.findCustomerById(1) → customers.get(1) → Customer{Sanjay}
  Prints: "Customer: Sanjay"
```

### 🔵 Choice 6: List Accounts by Customer

```
  First displays ALL accounts, then asks for Customer ID
  service.getAccountsByCustomerId(1) runs:
    → loops through all accounts
    → finds Account 101 (customerID=1) ✅
    → returns [Account 101]
  Prints: "Account ID: 101, Balance: 4000.0"
```

### 🔵 Choice 7: List Transactions by Account

```
  Asks: Account Id : ___  ← User types: 101
  service.getTransactionsByAccountId(101) runs:
    → loops through all transactions
    → finds Transaction 1 (accountID=101) ✅
    → finds Transaction 2 (accountID=101) ✅
    → returns [Transaction 1, Transaction 2]
  Prints both transactions with IDs, types, amounts, and timestamps
```

### 🔵 Choice 8: List Beneficiaries by Customer

```
  First displays ALL beneficiaries
  Then asks for Customer ID: 1
  service.getBeneficiariesByCustomerId(1):
    → loops through all beneficiaries
    → finds Beneficiary 501 (customerID=1) ✅
    → returns [Beneficiary 501]
  Prints: "Beneficiary ID: 501, Name: Priya"
```

### 🔵 Choice 9: Exit

```
  Prints "Thank you!"
  while (choice != 9) → 9 != 9 is FALSE → loop ends → main() returns → program exits
  All data in HashMaps is GONE (it was only in RAM).
```

---

## 8. Java Concepts Used — Mapped to Actual Code

| Concept | Where Used | Actual Code Example |
|---------|-----------|-------------------|
| **Class** | Every `.java` file | `public class Customer { }` |
| **Object** | When we create instances | `new Customer(1, "Sanjay", ...)` |
| **Constructor** | In all 4 entity classes | `public Customer(int customerID, ...)` |
| **Encapsulation** | All `private` fields + getters/setters | `private int customerID;` + `getCustomerID()` |
| **Interface** | `Bankingservice.java` | `public interface Bankingservice { }` |
| **implements** | `Bankingserviceimpl.java` | `class Bankingserviceimpl implements Bankingservice` |
| **@Override** | Every method in `Bankingserviceimpl` | `@Override public void addCustomer(...)` |
| **HashMap** | Data storage in `Bankingserviceimpl` | `new HashMap<>()` |
| **Generics** | Type-safe collections | `Map<Integer, Customer>`, `List<Account>` |
| **ArrayList** | Building filtered lists | `List<Account> result = new ArrayList<>()` |
| **Enhanced for-each** | Iterating collections | `for (Account a : accounts.values())` |
| **Scanner** | Reading user input | `scanner.nextLine().trim()` |
| **parseInt / parseDouble** | Converting String → number | `Integer.parseInt(scanner.nextLine())` |
| **Switch (arrow syntax)** | Menu dispatch | `case 1 -> addCustomer();` |
| **do-while loop** | Menu repetition | `do { ... } while (choice != 9);` |
| **static** | Class-level fields & methods | `private static final Scanner scanner` |
| **final** | Constants that never change | `private static final Bankingserviceimpl service` |
| **LocalDateTime** | Timestamp recording | `LocalDateTime.now()` |
| **toString() override** | Readable object printing | `@Override public String toString()` |
| **equalsIgnoreCase** | Case-insensitive comparison | `type.equalsIgnoreCase("Deposit")` |
| **Null check** | Safety before using objects | `if (account != null)` |
| **return (in void)** | Early exit from method | `return;` in failed withdrawal |
| **Post-increment** | Auto ID generation | `transactionCounter++` |
| **Package** | Code organization | `package com.tnsif.bankingservice` |
| **Module** | Java 9+ module system | `module ucet_codes { }` |

---

## 9. Quick-Reference Glossary

| Term | Plain English | In This Project |
|------|--------------|----------------|
| **Class** | A blueprint/template | `Customer.java` is the blueprint for all customers |
| **Object** | A filled-in copy of the blueprint | `new Customer(1, "Sanjay", ...)` creates one specific customer |
| **Field/Variable** | A piece of data stored in an object | `private String name;` inside Customer |
| **Method** | An action an object can perform | `getName()`, `setName()`, `addCustomer()` |
| **Constructor** | Special method that creates the object | `public Customer(int id, String name, ...)` |
| **Getter** | Method that **reads** a private field | `getCustomerID()` returns the ID |
| **Setter** | Method that **writes** a private field | `setCustomerID(5)` changes the ID to 5 |
| **Encapsulation** | Hiding data behind methods | Fields are `private`, accessed via `get`/`set` |
| **Interface** | A contract listing method signatures | `Bankingservice` says "these 15 methods must exist" |
| **Implementation** | A class that fulfills the contract | `Bankingserviceimpl` provides actual code for all 15 |
| **HashMap** | A key-value dictionary | `{1 → Customer{Sanjay}, 2 → Customer{Priya}}` |
| **Collection** | A group of objects | `Collection<Customer>` = a group of Customer objects |
| **List** | An ordered group of objects | `List<Account>` = accounts in order |
| **Generics** | Type safety for collections `<Type>` | `Map<Integer, Customer>` means keys are Integers, values are Customers |
| **Scanner** | Tool to read keyboard input | `scanner.nextLine()` reads what the user types |
| **static** | Belongs to the class itself, not to any object | `static void main()` — you don't create a BankingSystemApp object to call main |
| **final** | Cannot be changed after assignment | `final Scanner scanner` — can't point `scanner` to a different Scanner |
| **void** | Method returns nothing | `void addCustomer()` — just does work, no result back |
| **@Override** | "I'm replacing a parent/interface method" | Compiler error if the parent method doesn't exist |
| **null** | "Nothing" — the object doesn't exist | `findCustomerById(99)` returns `null` if ID 99 was never added |
| **do-while** | Loop that runs at least once, checks condition after | Menu always shows once, then asks: continue? |
| **switch** | Multi-way branch based on a value | `switch(choice)` with `case 1`, `case 2`, etc. |

---

> **🎓 Final Summary**: This project is 7 Java files, ~460 lines of code, that together simulate a mini banking system. It proves you can build OOP, apply collections, use interfaces, and manage real-world entity relationships — all in pure Core Java. Every line has a purpose. Every design choice follows a principle. And now, every reader understands exactly why. 🍦
