# Library Management System

A console-based Library Management System developed in Java. The
application allows library members to create accounts, sign in, search
for books, borrow and return books, view borrowing records, and manage
outstanding fines. Staff members have access to additional
library-management operations.

## Table of Contents

-   [Overview](#overview)
-   [Features](#features)
-   [Membership and Library Rules](#membership-and-library-rules)
-   [Technologies and Java Concepts](#technologies-and-java-concepts)
-   [Project Structure](#project-structure)
-   [Requirements](#requirements)
-   [Getting Started](#getting-started)
-   [Data Storage](#data-storage)
-   [How to Use the Application](#how-to-use-the-application)
-   [Validation and Exceptions](#validation-and-exceptions)
-   [Known Design Notes](#known-design-notes)
-   [Future Improvements](#future-improvements)

## Overview

The Library Management System is a menu-driven Java application that
models common library operations. It uses object-oriented programming,
fixed-size arrays for in-memory records, and text files for data
persistence.

The application is organised into packages so that user interaction,
business logic, data access, models, validation, and utility operations
are kept in separate areas.

## Features

### Member accounts

-   Register a library account.
-   Sign in and sign out.
-   Retrieve an account using the password-recovery workflow.
-   View and update a personal profile.
-   View membership status and outstanding fines.
-   Delete a personal account when the applicable business rules permit
    it.

### Book management

-   Add a book to the catalogue.
-   View all books and individual book details.
-   Search for books by title, author, and category.
-   Update book information.
-   Delete eligible books.
-   Track book availability and borrowing status.

### Borrowing and returns

-   Borrow an available book.
-   Enforce borrowing limits based on membership type.
-   Assign a borrowing date and due date.
-   Return a borrowed book.
-   Maintain borrowing records.
-   Calculate overdue days and applicable fines.

### Fines and membership status

-   Calculate fines based on membership type and overdue days.
-   Track a member's outstanding fine balance.
-   Accept fine payments.
-   Suspend a member when the configured fine threshold is reached.
-   Restore an eligible suspended member's status when the outstanding
    balance falls below the threshold.

### Staff and reports

-   Provide staff-only operations through the application menus and
    service logic.
-   View member and borrowing information.
-   View library statistics, including book counts, member counts,
    overdue books, and fines.

## Membership and Library Rules

The application defines three membership types:

  Membership type     Maximum concurrent books   Fine per overdue day
  ----------------- -------------------------- ----------------------
  Student                                    5                   ₦300
  Staff                                     10                   ₦500
  General                                    3                   ₦700

Other configured rules:

-   The standard borrowing period is **14 days**.
-   A member's account is suspended when the outstanding fine reaches
    **₦10,000**.
-   A book must be available before it can be borrowed.
-   A member cannot exceed the borrowing limit assigned to their
    membership type.
-   A member with unpaid fines or unreturned books is restricted from
    deleting their account.

These values are defined in the application's configuration and utility
logic. Update the relevant constants or methods if the library's
policies change.

## Technologies and Java Concepts

The project uses core Java features, including:

-   Classes and objects
-   Encapsulation and constructors
-   Enums
-   Interfaces
-   Method overloading and method overriding
-   Static fields and methods
-   Arrays
-   Conditional statements and loops
-   Exception handling and custom exceptions
-   File input/output
-   `BufferedReader` and `BufferedWriter`
-   Try-with-resources
-   `LocalDate` for date handling
-   Password hashing utilities
-   The Singleton design pattern for the database manager

No external database server or third-party library is required for the
basic application.

## Project Structure

The source code is under `src/com/library/`.

``` text
Library_Management_System/
├── src/
│   └── com/
│       └── library/
│           ├── Main.java
│           ├── api/
│           │   ├── Controller.java
│           │   ├── auth/
│           │   ├── book/
│           │   ├── user/
│           │   └── borrow_records/
│           ├── app_files/
│           │   └── Config.java
│           ├── database/
│           │   └── Database.java
│           ├── enums/
│           ├── exceptions/
│           ├── interfaces/
│           ├── models/
│           ├── repos/
│           ├── services/
│           └── utils/
├── users_file.txt
├── books_file.txt
├── borrowRecord_file.txt
└── README.md
```

### Package responsibilities

  Package                Responsibility
  ---------------------- ----------------------------------------------------------
  `api`                  Console controller and user-facing application workflows
  `api.auth`             Authentication-related controller operations
  `api.book`             Book controller operations
  `api.user`             Member/user controller operations
  `api.borrow_records`   Borrowing-record controller operations
  `app_files`            Application configuration and constants
  `database`             In-memory arrays and text-file persistence
  `enums`                Book, member, membership, and borrowing statuses
  `exceptions`           Custom exceptions for application-specific errors
  `interfaces`           Contracts implemented by repository classes
  `models`               Book, user, and borrowing-record data models
  `repos`                Data access and record management
  `services`             Business rules and application operations
  `utils`                Input, validation, authentication, and helper methods

## Requirements

-   **JDK 17 or later** is recommended.
-   A terminal or command prompt.
-   No Maven or Gradle installation is required for the source layout
    shown here.

The project uses modern Java language features, including switch
expressions. Use a JDK version that supports all language features
present in the source.

Check your Java installation:

``` bash
java -version
javac -version
```

Both commands should report a compatible JDK installation.

## Getting Started

### 1. Obtain the project

Download or clone the project repository, then open a terminal in the
project root directory (the directory containing `src`).

### 2. Compile the source files

On macOS or Linux:

``` bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
```

On Windows PowerShell:

``` powershell
New-Item -ItemType Directory -Force -Path out
$files = Get-ChildItem -Path src -Recurse -Filter *.java |
    ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $files
```

If compilation succeeds, the compiled `.class` files will be placed in
the `out` directory.

### 3. Run the application

From the project root:

``` bash
java -cp out com.library.Main
```

The main menu should appear in the terminal.

> Run the application from the project root so that the relative
> text-file paths resolve to the expected data files.

## Data Storage

The application uses text files as persistent storage:

  File                      Stored information
  ------------------------- ------------------------------
  `users_file.txt`          Member account records
  `books_file.txt`          Book catalogue records
  `borrowRecord_file.txt`   Borrowing and return records

The database manager loads stored records into fixed-size arrays when
the application starts. Changes made by the application are written back
to the relevant text files.

### Important data-handling notes

-   Keep the three data files in the project root unless the paths in
    `Config.java` are changed.
-   Back up the data files before testing changes to file-update or
    file-delete operations.
-   Do not manually change the delimiter-separated records unless you
    understand the record format expected by the loader.
-   Do not delete the data files if you want to retain existing members,
    books, and borrowing history.
-   The maximum in-memory capacities are configured in `Config.java`:
    -   Books: 500
    -   Members: 200
    -   Borrowing records: 1,000

The text files are local application storage; they are not a replacement
for a production database with transaction management, concurrent access
controls, and automated backups.

## How to Use the Application

1.  Start the application using the command above.
2.  Select the registration option to create a member account, or sign
    in with an existing account.
3.  Use the displayed menu to access the operations available to the
    signed-in account.
4.  Search for a book before attempting to borrow it.
5.  Return borrowed books through the return workflow.
6.  Check the account profile for outstanding fines and membership
    status.
7.  Use the fine-payment option to pay an outstanding balance.
8.  Sign out when finished.

Some operations are restricted to staff accounts. The application should
enforce those permissions in the relevant business/service operations,
not only by hiding menu choices.

## Validation and Exceptions

The application includes validation for common user inputs, including:

-   Names and text fields
-   Email addresses
-   Nigerian phone numbers
-   Password format
-   Membership type
-   Menu selections and numeric input

Custom exception classes are used to represent application errors such
as missing books or members, duplicate records, invalid credentials,
unavailable books, and borrowing-limit violations.

## Known Design Notes

This is an educational console application designed to practise Java
programming and object-oriented design. It uses fixed-size arrays and
flat text files to keep the implementation within the intended learning
scope.

For a production deployment, further work would be needed in areas such
as:

-   Stronger password-reset verification using time-limited tokens.
-   More comprehensive role-based access control.
-   Atomic and recoverable multi-file updates.
-   More robust validation of persisted records.
-   Automated unit and integration tests.
-   A database with transaction support.
-   Audit logging and secure handling of personal information.

## Future Improvements

Possible extensions include:

-   A graphical user interface or web interface.
-   A relational database such as PostgreSQL or MySQL.
-   More detailed borrowing history and fine receipts.
-   Book reservations and waitlists.
-   Automated reminders for approaching due dates.
-   Exportable library reports.
-   Automated tests for borrowing, returns, fines, and account
    permissions.

------------------------------------------------------------------------

## Author

Built by [Success.I.R (successdevop)]

## License

This project is open-source and available for learning purposes.
