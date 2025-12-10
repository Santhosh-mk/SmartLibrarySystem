package strategy;
public class K2600128_GuestFineStrategy implements K2600128_FineStrategy {
    @Override public long calculateFine(long daysLate) { return daysLate * 100L; } // 100/day
}
