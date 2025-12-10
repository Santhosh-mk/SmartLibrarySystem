package state;

import model.K2600128_Book;
import model.K2600128_User;

public interface K2600128_BookState {
    void borrow(K2600128_Book book, K2600128_User user);
    void returnBook(K2600128_Book book, K2600128_User user);
    void reserve(K2600128_Book book, K2600128_User user);
    boolean isAvailable();
}
