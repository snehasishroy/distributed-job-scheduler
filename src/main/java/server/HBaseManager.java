package server;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Objects;

public class HBaseManager {

    private Admin admin;
    private Connection connection;

    public HBaseManager() throws IOException {
        Configuration config = HBaseConfiguration.create();
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("hbase-site.xml"))
                .getPath();
        config.addResource(new Path(path));
        HBaseAdmin.available(config);
        connection = ConnectionFactory.createConnection(config);
        admin = connection.getAdmin();
    }

    public boolean tableExists(String name) throws IOException {
        TableName table = TableName.valueOf(name);
        return admin.tableExists(table);
    }

    public void createTable(String name, String columnFamily) throws IOException {
        if (!tableExists(name)) {
            TableName table = TableName.valueOf(name);
            HTableDescriptor descriptor = new HTableDescriptor(table);
            descriptor.addFamily(new HColumnDescriptor(columnFamily));
            admin.createTable(descriptor);
        }
    }

    public Table getTable(String name) throws IOException {
        TableName tableName = TableName.valueOf(name);
        return connection.getTable(tableName);
    }

    public void put(Table table, Put value) throws IOException {
        table.put(value);
    }

    public Result get(Table table, String id) throws IOException {
        Get key = new Get(Bytes.toBytes(id));
        return table.get(key);
    }
}
