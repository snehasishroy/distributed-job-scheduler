package models.job;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@Value
public class RepeatedJob extends Job {
    LocalDateTime startTime;
    LocalDateTime endTime;

    TimeUnit repeatIntervalTimeUnit;

    long repeatInterval;

    @Override
    public <T> T accept(JobVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
