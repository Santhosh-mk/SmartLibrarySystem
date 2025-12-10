package builder;

import model.K2600128_Book;

public class K2600128_BookBuilder {
    private String id, title, author, isbn, category;

    public K2600128_BookBuilder id(String id) { this.id = id; return this; }
    public K2600128_BookBuilder title(String title) { this.title = title; return this; }
    public K2600128_BookBuilder author(String author) { this.author = author; return this; }
    public K2600128_BookBuilder isbn(String isbn) { this.isbn = isbn; return this; }
    public K2600128_BookBuilder category(String category) { this.category = category; return this; }

    public K2600128_Book build() {
        return new K2600128_Book.Builder(id, title)
                .author(author)
                .isbn(isbn)
                .category(category)
                .build();
    }
}
