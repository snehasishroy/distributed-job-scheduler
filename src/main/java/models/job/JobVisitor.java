package models.job;

public interface JobVisitor<T> {
    T visit(ExactlyOnceJob job);

    T visit(RecurringJob job);

    T visit(RepeatedJob job);
}
