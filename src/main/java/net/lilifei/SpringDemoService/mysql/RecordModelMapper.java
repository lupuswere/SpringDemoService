package net.lilifei.SpringDemoService.mysql;

import net.lilifei.SpringDemoService.model.Record;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordModelMapper implements ModelMapper<Record> {

    @Override
    public Record map(final ResultSet resultSet) throws SQLException {
        return Record.builder()
                .recordId(resultSet.getString(1))
                .someProperty(resultSet.getString(2))
                .build();
    }
}
