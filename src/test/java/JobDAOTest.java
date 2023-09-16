import models.job.ExactlyOnceJob;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import server.JobDAO;

import java.io.IOException;
import java.util.UUID;

public class JobDAOTest {
    @Test
    public void testRegisterJob() throws IOException {
        JobDAO jobDAO = new JobDAO();
        String id = UUID.randomUUID().toString();
        ExactlyOnceJob exactlyOnceJob = ExactlyOnceJob.builder()
                .id(id)
                .callbackUrl("http://localhost:8080/test")
                .successStatusCode(500)
                .build();
        Assertions.assertDoesNotThrow(() -> jobDAO.registerJob(exactlyOnceJob));
        ExactlyOnceJob job = (ExactlyOnceJob) jobDAO.getJobDetails(id);
        Assertions.assertTrue(exactlyOnceJob.equals(job));
    }
}
