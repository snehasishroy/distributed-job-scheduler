package models.job;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Getter
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
public abstract class Job implements Serializable {
    String id;
    String callbackUrl;

    int successStatusCode;

    long relevancyWindow;

    TimeUnit relevancyWindowTimeUnit;

    public abstract <T> T accept(JobVisitor<T> visitor);
}