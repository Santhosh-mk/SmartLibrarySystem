package strategy;
public class K2600128_FacultyFineStrategy implements K2600128_FineStrategy {
    @Override public long calculateFine(long daysLate) { return daysLate * 20L; } // 20/day
}
