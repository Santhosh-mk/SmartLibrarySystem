package decorator;
import model.K2600128_Book;

public abstract class K2600128_BookDecorator extends K2600128_Book {
    protected K2600128_Book wrapped;

    public K2600128_BookDecorator(K2600128_Book wrapped) {
        super(new K2600128_Book.Builder(wrapped.getBookId(), wrapped.getTitle())
                .author(wrapped.getAuthor())
                .isbn(wrapped.getIsbn())
                .category(wrapped.getCategory())
                .build());
        this.wrapped = wrapped;
    }

    @Override
    public String getTitle() { return wrapped.getTitle(); }

    @Override
    public String getAuthor() { return wrapped.getAuthor(); }

    @Override
    public String getIsbn() { return wrapped.getIsbn(); }

    @Override
    public String getCategory() { return wrapped.getCategory(); }

    // Subclasses define extra behavior
    public abstract String getDescription();
}
