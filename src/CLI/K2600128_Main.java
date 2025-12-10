package CLI;

import command.K2600128_BorrowCommand;
import command.K2600128_Invoker;
import command.K2600128_ReserveCommand;
import command.K2600128_ReturnCommand;
import model.*;
import observer.K2600128_NotificationService;
import repository.*;
import service.*;
import strategy.*;

import java.util.Scanner;

public class K2600128_Main {

    private static K2600128_User loggedInUser = null;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Initialize repositories & services
        K2600128_BookRepository bookRepo = new K2600128_BookRepository();
        K2600128_UserRepository userRepo = new K2600128_UserRepository();
        K2600128_NotificationService notificationService = new K2600128_NotificationService();
        K2600128_BookService bookService = new K2600128_BookService(bookRepo, userRepo, notificationService);
        K2600128_ReportService reportService = new K2600128_ReportService(bookRepo, userRepo);
        K2600128_Invoker invoker = new K2600128_Invoker();

        // Seed default books and users
        seedData(bookService);

        System.out.println("Welcome to the Smart Library System!");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Login");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int option;
            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (option) {
                case 1 -> addBookMenu(sc, bookService);
                case 2 -> addUserMenu(sc, bookService);
                case 3 -> loginMenu(sc, userRepo, notificationService, bookService, invoker, reportService);
                case 0 -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // --- Login Menu ---
    private static void loginMenu(Scanner sc, K2600128_UserRepository userRepo, K2600128_NotificationService notificationService,
                                  K2600128_BookService bookService, K2600128_Invoker invoker, K2600128_ReportService reportService) {

        System.out.print("Enter User ID to login: ");
        String uid = sc.nextLine();
        K2600128_User user = userRepo.getUserById(uid);

        if (user != null) {
            loggedInUser = user;
            System.out.println("Login successful! Welcome, " + user.getName());

            // Show all pending notifications for this user
            bookService.notifyUserOnLogin(user);

            // Enter user actions menu
            userActionsMenu(sc, bookService, invoker, reportService);
        } else {
            System.out.println("User not found. Please try again.");
        }
    }


    // --- Menu after login ---
    private static void userActionsMenu(Scanner sc, K2600128_BookService bookService, K2600128_Invoker invoker, K2600128_ReportService reportService) {
        while (true) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. Reserve Book");
            System.out.println("4. View All Books");
            System.out.println("5. Undo Last Command");
            System.out.println("6. Reports");
            System.out.println("7. Update Book");
            System.out.println("8. Update User");
            System.out.println("9. Cancel Reservation");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            int option;
            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (option) {
                case 1 -> borrowBookMenu(sc, invoker, bookService);
                case 2 -> returnBookMenu(sc, invoker, bookService);
                case 3 -> reserveBookMenu(sc, invoker, bookService);
                case 4 -> bookService.listBooks();
                case 5 -> invoker.undo();
                case 6 -> showReportMenu(reportService, sc);
                case 7 -> updateBookMenu(sc, bookService);
                case 8 -> updateUserMenu(sc, bookService);
                case 9 -> cancelReservationMenu(sc, bookService);
                case 0 -> {
                    loggedInUser = null;
                    System.out.println("Logged out successfully.");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    // --- Menu Methods ---
    private static void addBookMenu(Scanner sc, K2600128_BookService bookService) {
        System.out.print("Book ID: ");
        String id = sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("ISBN: ");
        String isbn = sc.nextLine();
        System.out.print("Category: ");
        String cat = sc.nextLine();
        bookService.addBook(id, title, author, isbn, cat);
    }

    private static void addUserMenu(Scanner sc, K2600128_BookService bookService) {
        System.out.print("User ID: ");
        String uid = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Contact: ");
        String contact = sc.nextLine();

        System.out.println("Select Membership Type:");
        System.out.println("1. Student");
        System.out.println("2. Faculty");
        System.out.println("3. Guest");
        System.out.print("Choose (1-3): ");

        int memChoice = Integer.parseInt(sc.nextLine());

        K2600128_MembershipType mt;
        K2600128_FineStrategy strat;
        switch (memChoice) {
            case 2 -> {
                mt = K2600128_MembershipType.FACULTY;
                strat = new K2600128_FacultyFineStrategy();
            }
            case 3 -> {
                mt = K2600128_MembershipType.GUEST;
                strat = new K2600128_GuestFineStrategy();
            }
            default -> {
                mt = K2600128_MembershipType.STUDENT;
                strat = new K2600128_StudentFineStrategy();
            }
        }

        K2600128_User user = new K2600128_User(uid, name, email, contact, mt, strat);
        bookService.addUser(user);
    }

    private static void borrowBookMenu(Scanner sc, K2600128_Invoker invoker, K2600128_BookService bookService) {
        System.out.print("Book ID: ");
        String bookId = sc.nextLine();
        invoker.execute(new K2600128_BorrowCommand(bookService, bookId, loggedInUser.getUserId()));
    }

    private static void returnBookMenu(Scanner sc, K2600128_Invoker invoker, K2600128_BookService bookService) {
        System.out.print("Book ID: ");
        String bookId = sc.nextLine();
        invoker.execute(new K2600128_ReturnCommand(bookService, bookId, loggedInUser.getUserId()));
    }

    private static void reserveBookMenu(Scanner sc, K2600128_Invoker invoker, K2600128_BookService bookService) {
        System.out.print("Book ID: ");
        String bookId = sc.nextLine();
        invoker.execute(new K2600128_ReserveCommand(bookService, bookId, loggedInUser.getUserId()));
    }

    private static void updateBookMenu(Scanner sc, K2600128_BookService bookService) {
        System.out.print("Book ID to update: ");
        String id = sc.nextLine();
        System.out.print("New Title: ");
        String title = sc.nextLine();
        System.out.print("New Author: ");
        String author = sc.nextLine();
        System.out.print("New ISBN: ");
        String isbn = sc.nextLine();
        System.out.print("New Category: ");
        String cat = sc.nextLine();
        bookService.updateBook(id, title, author, isbn, cat);
    }

    private static void updateUserMenu(Scanner sc, K2600128_BookService bookService) {
        System.out.print("User ID to update: ");
        String uid = sc.nextLine();
        System.out.print("New Name: ");
        String name = sc.nextLine();
        System.out.print("New Email: ");
        String email = sc.nextLine();
        System.out.print("New Contact: ");
        String contact = sc.nextLine();

        System.out.println("Select Membership Type:");
        System.out.println("1. Student");
        System.out.println("2. Faculty");
        System.out.println("3. Guest");
        System.out.print("Choose (1-3): ");
        int memChoice = Integer.parseInt(sc.nextLine());

        K2600128_MembershipType mt;
        K2600128_FineStrategy strat;
        switch (memChoice) {
            case 2 -> {
                mt = K2600128_MembershipType.FACULTY;
                strat = new K2600128_FacultyFineStrategy();
            }
            case 3 -> {
                mt = K2600128_MembershipType.GUEST;
                strat = new K2600128_GuestFineStrategy();
            }
            default -> {
                mt = K2600128_MembershipType.STUDENT;
                strat = new K2600128_StudentFineStrategy();
            }
        }

        bookService.updateUser(uid, name, email, contact, mt, strat);
    }

    private static void cancelReservationMenu(Scanner sc, K2600128_BookService bookService) {
        System.out.print("Book ID: ");
        String bookId = sc.nextLine();
        bookService.cancelReservation(bookId, loggedInUser.getUserId());
    }

    private static void showReportMenu(K2600128_ReportService reportService, Scanner sc) {
        while (true) {
            System.out.println("\n--- REPORTS MENU ---");
            System.out.println("1. Most Borrowed Books");
            System.out.println("2. Active Borrowers");
            System.out.println("3. Overdue Books");
            System.out.println("0. Back to User Menu");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter top N books to display: ");
                    int topBooks = Integer.parseInt(sc.nextLine());
                    reportService.mostBorrowedBooks(topBooks);
                }
                case 2 -> {
                    System.out.print("Enter top N users to display: ");
                    int topUsers = Integer.parseInt(sc.nextLine());
                    reportService.activeBorrowers(topUsers);
                }
                case 3 -> reportService.overdueBooks();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void seedData(K2600128_BookService bookService) {
        // Default Books
        bookService.addBook("B01", "Java Fundamentals", "John Doe", "978-1234567890", "Programming");
        bookService.addBook("B02", "Python Essentials", "Jane Smith", "978-0987654321", "Programming");
        bookService.addBook("B03", "Data Structures", "Alan Turing", "978-1122334455", "Computer Science");
        bookService.addBook("B04", "Algorithms Unlocked", "Robert Sedgewick", "978-6677889900", "Computer Science");
        bookService.addBook("B05", "Clean Code", "Robert Martin", "978-0132350884", "Programming");

        // Default Users
        K2600128_User u1 = new K2600128_User("U01", "Admin", "admin@example.com", "0712345678", K2600128_MembershipType.STUDENT, new K2600128_StudentFineStrategy());
        K2600128_User u2 = new K2600128_User("U02", "Bob", "bob@example.com", "0723456789", K2600128_MembershipType.FACULTY, new K2600128_FacultyFineStrategy());
        K2600128_User u3 = new K2600128_User("U03", "Charlie", "charlie@example.com", "0734567890", K2600128_MembershipType.GUEST, new K2600128_GuestFineStrategy());

        bookService.addUser(u1);
        bookService.addUser(u2);
        bookService.addUser(u3);
    }
}
