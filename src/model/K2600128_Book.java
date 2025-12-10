package model;

import state.*;
import java.util.*;

public class K2600128_Book {

    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private String category;

    // Optional metadata
    private String edition;
    private List<String> tags;
    private List<String> reviews;

    private K2600128_BookState state;
    private Queue<K2600128_User> reservationQueue = new LinkedList<>();
    private List<K2600128_BorrowRecord> borrowHistory = new ArrayList<>();

    private int borrowCount = 0;

    // -----------------------
    // PRIVATE CONSTRUCTOR (Builder)
    // -----------------------
    private K2600128_Book(Builder builder) {
        this.bookId = builder.bookId;
        this.title = builder.title;
        this.author = builder.author;
        this.isbn = builder.isbn;
        this.category = builder.category;

        this.edition = builder.edition;
        this.tags = builder.tags;
        this.reviews = builder.reviews;

        this.state = new K2600128_AvailableState();
    }

    // -----------------------
    // PUBLIC COPY CONSTRUCTOR (for Decorator)
    // -----------------------
    public K2600128_Book(K2600128_Book other) {
        this.bookId = other.bookId;
        this.title = other.title;
        this.author = other.author;
        this.isbn = other.isbn;
        this.category = other.category;

        this.edition = other.edition;
        this.tags = new ArrayList<>(other.tags);
        this.reviews = new ArrayList<>(other.reviews);

        this.state = other.state;
        this.reservationQueue = new LinkedList<>(other.reservationQueue);
        this.borrowHistory = new ArrayList<>(other.borrowHistory);
        this.borrowCount = other.borrowCount;
    }

    // -----------------------
    // BUILDER CLASS
    // -----------------------
    public static class Builder {
        private String bookId;
        private String title;
        private String author;
        private String isbn;
        private String category;

        private String edition;
        private List<String> tags = new ArrayList<>();
        private List<String> reviews = new ArrayList<>();

        public Builder(String bookId, String title) {
            this.bookId = bookId;
            this.title = title;
        }

        public Builder author(String author) { this.author = author; return this; }
        public Builder isbn(String isbn) { this.isbn = isbn; return this; }
        public Builder category(String category) { this.category = category; return this; }
        public Builder edition(String edition) { this.edition = edition; return this; }
        public Builder tag(String tag) { this.tags.add(tag); return this; }
        public Builder review(String review) { this.reviews.add(review); return this; }

        public K2600128_Book build() { return new K2600128_Book(this); }
    }

    // -----------------------
    // GETTERS
    // -----------------------
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getCategory() { return category; }
    public String getEdition() { return edition; }
    public List<String> getTags() { return tags; }
    public List<String> getReviews() { return reviews; }

    public K2600128_BookState getState() { return state; }
    public void setState(K2600128_BookState state) { this.state = state; }

    public Queue<K2600128_User> getReservationQueue() { return reservationQueue; }
    public List<K2600128_BorrowRecord> getBorrowHistory() { return borrowHistory; }

    public int getBorrowCount() { return borrowCount; }

    public void addBorrowRecord(K2600128_BorrowRecord r) {
        borrowHistory.add(r);
        borrowCount++;
    }

    // -----------------------
    // SETTERS
    // -----------------------
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setCategory(String category) { this.category = category; }
}
