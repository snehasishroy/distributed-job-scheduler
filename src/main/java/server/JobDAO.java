package server;

import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import models.Plan;
import models.job.*;
import org.apache.commons.lang.SerializationUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
public class JobDAO {
    HBaseManager hBaseManager;
    String columnFamily = "cf";
    String data = "data";
    String tableName = "jobDetails";
    Table table;

    public JobDAO() throws IOException {
        hBaseManager = new HBaseManager();
        hBaseManager.createTable(tableName, columnFamily);
        table = hBaseManager.getTable(tableName);
    }

    public void registerJob(Job job) throws IOException {
        byte[] row = Bytes.toBytes(job.getId());
        Put put = new Put(row);
        put.addColumn(columnFamily.getBytes(), data.getBytes(), SerializationUtils.serialize(job));
        hBaseManager.put(table, put);


    }

    @VisibleForTesting
    public Plan generatePlan(Job job) {
        return job.accept(new JobVisitor<Plan>() {
            @Override
            public Plan visit(ExactlyOnceJob job) {
                return Plan.builder()
                        .jobId(job.getId())
                        .planId(getRandomID())
                        .expectedExecutionTime(job.getDateTime())
                        .build();
            }

            @Override
            public Plan visit(RecurringJob job) {
                return Plan.builder()
                        .jobId(job.getId())
                        .planId(getRandomID())
                        .expectedExecutionTime(job.getDateTimes().get(0))
                        .build();
            }

            @Override
            public Plan visit(RepeatedJob job) {
                return null;
            }
        });
    }

    public Job getJobDetails(String id) throws IOException {
        Result result = hBaseManager.get(table, id);
        byte[] value = result.getValue(columnFamily.getBytes(), data.getBytes());
        Job job = (Job) SerializationUtils.deserialize(value);
        return job;
    }

    private String getRandomID() {
        return UUID.randomUUID().toString();
    }
}
