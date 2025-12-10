package state;

import model.K2600128_Book;
import model.K2600128_User;

public class K2600128_BorrowedState implements K2600128_BookState {

    @Override
    public void borrow(K2600128_Book book, K2600128_User user) {
        System.out.println("Book is already borrowed.");
    }

    @Override
    public void returnBook(K2600128_Book book, K2600128_User user) {
        if (!book.getReservationQueue().isEmpty()) {
            book.setState(new K2600128_ReservedState());
        } else {
            book.setState(new K2600128_AvailableState());
        }
    }

    @Override
    public boolean isAvailable() {
        return false; // borrowed books are not available
    }

    @Override
    public void reserve(K2600128_Book book, K2600128_User user) {
        if (!book.getReservationQueue().contains(user)) {
            book.getReservationQueue().add(user);
            user.addReservedBook(book);
            System.out.println("Book reserved successfully. Your position in the queue: " + book.getReservationQueue().size());
        } else {
            System.out.println("User already in reservation queue.");
        }
    }
}
