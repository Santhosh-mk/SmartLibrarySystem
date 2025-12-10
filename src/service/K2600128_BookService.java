package service;

import model.K2600128_Book;
import model.K2600128_BorrowRecord;
import model.K2600128_User;
import model.K2600128_MembershipType;
import strategy.K2600128_FineStrategy;

import repository.K2600128_BookRepository;
import repository.K2600128_UserRepository;

import observer.K2600128_NotificationService;

import state.K2600128_AvailableState;
import state.K2600128_BorrowedState;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class K2600128_BookService {

    private final K2600128_BookRepository bookRepo;
    private final K2600128_UserRepository userRepo;
    private final K2600128_NotificationService notificationService;

    public K2600128_BookService(K2600128_BookRepository bookRepo, K2600128_UserRepository userRepo, K2600128_NotificationService notificationService) {
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
        this.notificationService = notificationService;
    }

    // -------------------------
    // BOOK MANAGEMENT
    // -------------------------
    public void addBook(String bookId, String title, String author, String isbn, String category) {
        K2600128_Book book = new K2600128_Book.Builder(bookId, title)
                .author(author)
                .isbn(isbn)
                .category(category)
                .build();

        bookRepo.addBook(book);
        System.out.println("Book added successfully.");
    }

    public void updateBook(String id, String newTitle, String newAuthor, String newIsbn, String newCategory) {
        K2600128_Book book = bookRepo.getBook(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setIsbn(newIsbn);
        book.setCategory(newCategory);
        System.out.println("Book updated successfully.");
    }

    public void deleteBook(String id) {
        boolean removed = bookRepo.removeBook(id);
        System.out.println(removed ? "Book deleted." : "Book not found.");
    }

    public void cancelReservation(String bookId, String userId) {
        K2600128_Book book = bookRepo.getBook(bookId);
        K2600128_User user = userRepo.getUserById(userId);

        if (book == null || user == null) {
            System.out.println("Invalid book or user.");
            return;
        }

        if (book.getReservationQueue().remove(user)) {
            user.getReservedBooks().remove(book);
            System.out.println("Reservation cancelled.");
        } else {
            System.out.println("User did not reserve this book.");
        }
    }

    // -------------------------
    // USER MANAGEMENT
    // -------------------------
    public void addUser(K2600128_User user) {
        userRepo.addUser(user);
        System.out.println("User added successfully.");
    }

    public void updateUser(String userId, String newName, String newEmail, String newContact,
                           K2600128_MembershipType newMembershipType, K2600128_FineStrategy newStrategy) {
        K2600128_User user = userRepo.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        user.setName(newName);
        user.setEmail(newEmail);
        user.setContactNumber(newContact);
        user.setMembershipType(newMembershipType);
        user.setFineStrategy(newStrategy);

        System.out.println("User updated successfully.");
    }

    // -------------------------
    // BORROW BOOK
    // -------------------------
    public void borrowBook(String bookId, String userId) {
        K2600128_Book book = bookRepo.getBook(bookId);
        K2600128_User user = userRepo.getUserById(userId);

        if (book == null || user == null) {
            System.out.println("Invalid book or user.");
            return;
        }

        if (!(book.getState() instanceof K2600128_AvailableState)) {
            System.out.println("Book is not available for borrowing.");
            return;
        }

        int limit = user.getMembershipType().getBorrowLimit();
        if (user.getCurrentlyBorrowed().size() >= limit) {
            System.out.println("Borrow limit reached for user.");
            return;
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(user.getMembershipType().getLoanDays());

        K2600128_BorrowRecord record = new K2600128_BorrowRecord(book, user, borrowDate, dueDate);
        book.addBorrowRecord(record);
        user.getBorrowedHistory().add(record);
        user.getCurrentlyBorrowed().add(book);

        book.getState().borrow(book, user);

        System.out.println("Book borrowed successfully. Due date: " + dueDate);
    }

    // -------------------------
    // RETURN BOOK
    // -------------------------
    public void returnBook(String bookId, String userId) {
        K2600128_Book book = bookRepo.getBook(bookId);
        K2600128_User user = userRepo.getUserById(userId);

        if (book == null || user == null) {
            System.out.println("Invalid book or user.");
            return;
        }

        if (!user.getCurrentlyBorrowed().contains(book)) {
            System.out.println("This user did not borrow this book.");
            return;
        }

        K2600128_BorrowRecord active = null;
        for (K2600128_BorrowRecord r : user.getBorrowedHistory()) {
            if (r.getBook().equals(book) && r.getReturnDate() == null) {
                active = r;
                break;
            }
        }

        if (active == null) {
            System.out.println("No active borrow record.");
            return;
        }

        LocalDate returnDate = LocalDate.now();
        active.setReturnDate(returnDate);

        long daysLate = ChronoUnit.DAYS.between(active.getDueDate(), returnDate);
        if (daysLate < 0) daysLate = 0;

        long fine = 0;
        if (user.getFineStrategy() != null) {
            fine = user.getFineStrategy().calculateFine(daysLate);
        }
        active.setFinePaid(fine);

        user.getCurrentlyBorrowed().remove(book);

        // Handle reservation queue
        if (!book.getReservationQueue().isEmpty()) {
            book.getState().returnBook(book, user);

            K2600128_User nextUser = book.getReservationQueue().poll();
            if (nextUser != null) {
                nextUser.getReservedBooks().remove(book);
                notificationService.notifyReservedBook(nextUser, book.getTitle()); // store notification for next user
            }

            System.out.println("Book returned. Reserved for next user. Fine: LKR " + fine);
        } else {
            book.getState().returnBook(book, user);
            System.out.println("Book returned. Fine: LKR " + fine);
        }
    }

    // -------------------------
    // RESERVE BOOK
    // -------------------------
    public void reserveBook(String bookId, String userId) {
        K2600128_Book book = bookRepo.getBook(bookId);
        K2600128_User user = userRepo.getUserById(userId);

        if (book == null || user == null) {
            System.out.println("Invalid book or user.");
            return;
        }

        if (!(book.getState() instanceof K2600128_BorrowedState)) {
            System.out.println("Reservations allowed only for borrowed books.");
            return;
        }

        if (book.getReservationQueue().contains(user)) {
            System.out.println("User already in reservation queue.");
            return;
        }

        book.getReservationQueue().add(user);
        user.addReservedBook(book);
        System.out.println("Book reserved successfully. Your position in the queue: " + book.getReservationQueue().size());
    }

    // -------------------------
    // LIST BOOKS
    // -------------------------
    public void listBooks() {
        for (K2600128_Book book : bookRepo.getAllBooks().values()) {
            System.out.print(book.getBookId() + " - " + book.getTitle() + " (" +
                    book.getState().getClass().getSimpleName() + ")");
            if (!book.getReservationQueue().isEmpty()) {
                System.out.print(" | Reserved by: ");
                book.getReservationQueue().forEach(u -> System.out.print(u.getName() + " "));
            }
            System.out.println();
        }
    }

    // -------------------------
    // NOTIFY USER ON LOGIN
    // -------------------------
    public void notifyUserOnLogin(K2600128_User user) {
        notificationService.notifyUserOnLogin(user);
    }
}
