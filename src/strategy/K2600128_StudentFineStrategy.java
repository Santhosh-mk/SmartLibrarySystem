package strategy;
public class K2600128_StudentFineStrategy implements K2600128_FineStrategy {
    @Override public long calculateFine(long daysLate) { return daysLate * 50L; } // 50/day
}
