package repository;

import model.K2600128_Book;
import java.util.*;

public class K2600128_BookRepository {

    private Map<String, K2600128_Book> books = new HashMap<>();

    // Add a book
    public void addBook(K2600128_Book b) {
        books.put(b.getBookId(), b);
    }

    // Get a book by ID
    public K2600128_Book getBook(String id) {
        return books.get(id);
    }

    // Get all books as a Map (for BookService to use .values())
    public Map<String, K2600128_Book> getAllBooks() {
        return books;
    }

    // Update a book
    public boolean updateBook(String id, K2600128_Book updated) {
        if (!books.containsKey(id)) return false;
        books.put(id, updated);
        return true;
    }

    // Remove a book
    public boolean removeBook(String id) {
        return books.remove(id) != null;
    }
}
