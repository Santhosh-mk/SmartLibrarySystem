package model;

import strategy.K2600128_FineStrategy;
import java.util.ArrayList;
import java.util.List;

public class K2600128_User {

    private String userId;
    private String name;
    private String email;
    private String contactNumber;

    private K2600128_MembershipType membershipType;
    private K2600128_FineStrategy fineStrategy;

    private List<K2600128_BorrowRecord> borrowedHistory = new ArrayList<>();
    private List<K2600128_Book> currentlyBorrowed = new ArrayList<>();
    private List<K2600128_Book> reservedBooks = new ArrayList<>();

    // Store notifications for the user
    private List<String> notifications = new ArrayList<>();

    public K2600128_User(String userId, String name, String email, String contactNumber,
                         K2600128_MembershipType membershipType, K2600128_FineStrategy fineStrategy) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.membershipType = membershipType;
        this.fineStrategy = fineStrategy;
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public K2600128_MembershipType getMembershipType() { return membershipType; }
    public void setMembershipType(K2600128_MembershipType membershipType) { this.membershipType = membershipType; }
    public K2600128_FineStrategy getFineStrategy() { return fineStrategy; }
    public void setFineStrategy(K2600128_FineStrategy fineStrategy) { this.fineStrategy = fineStrategy; }
    public List<K2600128_BorrowRecord> getBorrowedHistory() { return borrowedHistory; }
    public List<K2600128_Book> getCurrentlyBorrowed() { return currentlyBorrowed; }
    public List<K2600128_Book> getReservedBooks() { return reservedBooks; }
    public void addReservedBook(K2600128_Book book) { reservedBooks.add(book); }
    public void removeReservedBook(K2600128_Book book) { reservedBooks.remove(book); }

    // Notifications
    public void addNotification(String message) { notifications.add(message); }
    public List<String> getNotifications() { return notifications; }
    public void clearNotifications() { notifications.clear(); }
}
