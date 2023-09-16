package models.job;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@Value
public class RecurringJob extends Job {
    List<LocalDateTime> dateTimes;

    @Override
    public <T> T accept(JobVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
