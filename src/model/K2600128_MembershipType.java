package model;

public enum K2600128_MembershipType {
    STUDENT(14, 5),   // 14 days, limit 5
    FACULTY(30, 10),  // 30 days, limit 10
    GUEST(7, 2);      // 7 days, limit 2 (example)

    private final int loanDays;
    private final int borrowLimit;

    K2600128_MembershipType(int loanDays, int borrowLimit) {
        this.loanDays = loanDays;
        this.borrowLimit = borrowLimit;
    }

    public int getLoanDays() { return loanDays; }
    public int getBorrowLimit() { return borrowLimit; }
}
