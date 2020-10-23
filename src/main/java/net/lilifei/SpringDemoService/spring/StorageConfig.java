package net.lilifei.SpringDemoService.spring;

import com.mysql.cj.jdbc.MysqlDataSource;

import net.lilifei.SpringDemoService.model.Record;
import net.lilifei.SpringDemoService.mysql.MysqlRecordStorage;
import net.lilifei.SpringDemoService.mysql.RecordModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class StorageConfig {

    @Bean
    MysqlDataSource mysqlDataSource() {
        final MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setServerName("localhost");
        mysqlDataSource.setDatabaseName("demo");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("");
        try {
            mysqlDataSource.setServerTimezone("UTC");
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
        return mysqlDataSource;
    }

    @Bean
    @Autowired
    MysqlRecordStorage mysqlRecordStorage(final MysqlDataSource mysqlDataSource) {
        return MysqlRecordStorage.builder()
                .mysqlDataSource(mysqlDataSource)
                .modelMapper(Record.class, new RecordModelMapper())
                .build();
    }
}
