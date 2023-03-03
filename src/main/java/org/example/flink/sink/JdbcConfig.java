package org.example.flink.sink;

import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;

public class JdbcConfig {
    public static JdbcExecutionOptions getExecutionConfig() {
        return JdbcExecutionOptions.builder()
                                   .withBatchSize(1000)
                                   .withBatchIntervalMs(200)
                                   .withMaxRetries(5)
                                   .build();
    }

    public static JdbcConnectionOptions getConnectionOptions() {
        return new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                .withUrl("jdbc:mysql://localhost:3306/db")
                .withUsername("user")
                .withPassword("password")
                .withDriverName("com.mysql.cj.jdbc.Driver")
                .build();
    }

}
