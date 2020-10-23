package net.lilifei.SpringDemoService.spring;

import net.lilifei.SpringDemoService.mysql.MysqlRecordStorage;
import net.lilifei.SpringDemoService.storage.RecordStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Autowired
    MysqlRecordStorage mysqlRecordStorage;

    @Bean
    RecordStorage recordStorage() {
        return mysqlRecordStorage;
    }
}
