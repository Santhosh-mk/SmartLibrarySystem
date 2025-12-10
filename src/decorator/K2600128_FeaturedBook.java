package decorator;

import model.K2600128_Book;

public class K2600128_FeaturedBook extends K2600128_BookDecorator {

    public K2600128_FeaturedBook(K2600128_Book wrapped) {
        super(wrapped);
    }

    // Implement the abstract method
    @Override
    public String getDescription() {
        return "★ Featured: " + wrapped.getTitle() + " by " + wrapped.getAuthor();
    }

    // Optional: additional feature
    public String getFeature() {
        return "Featured";
    }
}
