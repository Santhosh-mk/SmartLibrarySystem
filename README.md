📘 Smart Library Management System – README.md
📌 Overview

The Smart Library Management System is a Java-based console application developed using object-oriented programming and multiple software design patterns. The system provides a structured and scalable solution for managing library operations such as user registration, book management, borrowing, returning, fine calculation, reservations, notifications and reporting.

This project was developed as part of an academic coursework submission (Student ID: K2600128) and demonstrates clean architecture, maintainability, and extensibility following industry best practices.

🚀 Features
User & Book Management

Add new users with membership types

Add new books with ISBN, author, category and ID

Update existing book/user details

Borrowing & Returning

Borrow books based on membership borrowing limits

Automatic due date generation

Fine calculation for overdue returns using Strategy Pattern

Reservation System

Reserve books that are already borrowed

Queue-based reservation handling

Auto-assignment when the book becomes available

Users receive notifications using the Observer Pattern

Command Execution

Borrow, Return, Reserve actions implemented as independent commands

Undo last command (Command Pattern)

Reporting

Generate various library reports

View most borrowed books

List overdue books and fine details

Other Features

Console-based interactive menu

Clear system messages and validations

Pattern-driven architecture for easy enhancements

🏗️ Architecture

The system follows a modular architecture supported by industry-standard design patterns.
Key layers include:

✔ Model Layer

Contains entities like Book, User, BorrowRecord, etc.

✔ Service Layer

Handles core business logic such as borrowing, returning, and reporting.

✔ Repository Layer

Stores and manages collections of books and users.

✔ CLI Layer

Provides command-line interactions for the user.

✔ Pattern Implementations

Each pattern solves a specific design problem and supports long-term maintainability.

🎨 Design Patterns Used
1. Strategy Pattern (Fine Calculation)

Different membership types (Student, Faculty, Guest) use different fine calculation strategies.

2. State Pattern (Book Availability)

Books change behaviour dynamically depending on their state:

Available

Borrowed

Reserved

3. Observer Pattern (Notifications)

Users are notified automatically when:

A reserved book becomes available

A book is returned

4. Command Pattern (Borrow/Return/Reserve)

Encapsulates actions into objects for undoable, clean execution.

5. Decorator Pattern (Book Enhancements)

Allows extending book behaviour (e.g., special edition, formatting) without modifying the base Book class.

6. Builder Pattern (Book Construction)

Simplifies object creation and avoids telescoping constructors.

🧩 UML Diagram

(Include your class diagram image here)

![UML Diagram](path/to/uml.png)

⚙️ Installation & Setup
Prerequisites

Java JDK 17+ (JDK 25 recommended)

IntelliJ IDEA / VSCode / Eclipse

Git installed (optional)

Clone this repository
git clone https://github.com/yourusername/SmartLibrarySystem.git

Compile the project
javac -d out src/**/*.java

Run the application
java -cp out CLI.Main

🖥️ Usage

Once the program runs, you will see the Main Menu:

1. Add Book
2. Add User
3. Login
0. Exit


After logging in, users can:

Borrow books

Return books

Reserve books

Generate reports

Undo commands

View all books

Update user/book details

📁 Folder Structure
SmartLibrarySystem/
│
├── src/
│   ├── CLI/
│   ├── command/
│   ├── decorator/
│   ├── model/
│   ├── observer/
│   ├── repository/
│   ├── service/
│   ├── strategy/
│   └── state/
│
├── out/
├── README.md
└── UML.png

🔮 Future Improvements

Graphical User Interface (JavaFX or Swing)

Database integration (MySQL / PostgreSQL)

User authentication with passwords

Exportable reports (PDF/CSV)

Email-based notifications

Admin dashboard for analytics

📜 License

This project is for educational purposes and may be reused or extended with proper credit.

👤 Author

Santhosh Milan Kumar (K2600128)
Smart Library Management System – Coursework Project
