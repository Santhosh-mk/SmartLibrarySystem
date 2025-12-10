package command;

import service.K2600128_BookService;

public class K2600128_BorrowCommand implements K2600128_Command {
    private K2600128_BookService service;
    private String bookId;
    private String userId;

    public K2600128_BorrowCommand(K2600128_BookService service, String bookId, String userId) {
        this.service = service; this.bookId = bookId; this.userId = userId;
    }

    @Override
    public void execute() { service.borrowBook(bookId, userId); }
    @Override
    public void undo() { service.returnBook(bookId, userId); }
}
