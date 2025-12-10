package service;

import model.K2600128_Book;
import model.K2600128_BorrowRecord;
import model.K2600128_User;
import repository.K2600128_BookRepository;
import repository.K2600128_UserRepository;

import java.time.LocalDate;
import java.util.*;

public class K2600128_ReportService {
    private K2600128_BookRepository bookRepo;
    private K2600128_UserRepository userRepo;

    public K2600128_ReportService(K2600128_BookRepository br, K2600128_UserRepository ur) {
        this.bookRepo = br;
        this.userRepo = ur;
    }

    // -------------------------
    // Most Borrowed Books
    // -------------------------
    public void mostBorrowedBooks(int topN) {
        // Get all books as a list from the map values
        List<K2600128_Book> list = new ArrayList<>(bookRepo.getAllBooks().values());
        list.sort(Comparator.comparingInt(K2600128_Book::getBorrowCount).reversed());

        System.out.println("Top " + topN + " borrowed books:");
        list.stream()
                .limit(topN)
                .forEach(b -> System.out.println(b.getTitle() + " - " + b.getBorrowCount()));
    }

    // -------------------------
    // Active Borrowers
    // -------------------------
    public void activeBorrowers(int topN) {
        // Get all users as a list
        List<K2600128_User> users = new ArrayList<>(userRepo.getAllUsers()); // assuming getAllUsers() returns Collection<User>
        users.sort((a, b) -> Integer.compare(b.getCurrentlyBorrowed().size(), a.getCurrentlyBorrowed().size()));

        System.out.println("Top " + topN + " active borrowers:");
        users.stream()
                .limit(topN)
                .forEach(u -> System.out.println(u.getUserId() + " - " + u.getName() + " borrowed " + u.getCurrentlyBorrowed().size()));
    }

    // -------------------------
    // Overdue Books
    // -------------------------
    public void overdueBooks() {
        LocalDate today = LocalDate.now();
        System.out.println("Overdue books:");

        // Iterate over map values
        for (K2600128_Book b : bookRepo.getAllBooks().values()) {
            for (K2600128_BorrowRecord r : b.getBorrowHistory()) {
                if (r.getReturnDate() == null && r.getDueDate().isBefore(today)) {
                    System.out.println(b.getTitle() + " - borrowed by " + r.getUser().getUserId() + " due " + r.getDueDate());
                }
            }
        }
    }
}
