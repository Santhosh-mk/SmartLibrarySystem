package model;

import java.time.LocalDate;

public class K2600128_BorrowRecord {

    private K2600128_Book book;
    private K2600128_User user;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private long finePaid;

    public K2600128_BorrowRecord(K2600128_Book book, K2600128_User user, LocalDate borrowDate, LocalDate dueDate) {
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public K2600128_Book getBook() { return book; }
    public K2600128_User getUser() { return user; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public long getFinePaid() { return finePaid; }

    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setFinePaid(long finePaid) { this.finePaid = finePaid; }
}
