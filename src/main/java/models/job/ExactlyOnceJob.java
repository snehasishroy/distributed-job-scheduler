package models.job;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class ExactlyOnceJob extends Job {
    LocalDateTime dateTime;

    @Override
    public <T> T accept(JobVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
