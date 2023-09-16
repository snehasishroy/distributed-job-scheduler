package models;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Builder
@Value
public class Plan implements Serializable {
    String planId;
    String jobId;
    LocalDateTime expectedExecutionTime;

    @Builder.Default
    Optional<LocalDateTime> actualExecutionTime = Optional.empty();

    
}
