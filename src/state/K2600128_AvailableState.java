package state;

import model.K2600128_Book;
import model.K2600128_User;

public class K2600128_AvailableState implements K2600128_BookState {

    @Override
    public void borrow(K2600128_Book book, K2600128_User user) {
        book.setState(new K2600128_BorrowedState());
        System.out.println("Book borrowed successfully.");
    }

    @Override
    public void returnBook(K2600128_Book book, K2600128_User user) {
        System.out.println("Book is already available.");
    }

    @Override
    public boolean isAvailable() {
        return true; // available books are indeed available
    }

    @Override
    public void reserve(K2600128_Book book, K2600128_User user) {
        System.out.println("Book is available, no need to reserve.");
    }
}
