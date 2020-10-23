package net.lilifei.SpringDemoService.mysql;

import com.google.common.collect.ImmutableList;
import com.mysql.cj.jdbc.MysqlDataSource;

import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import net.lilifei.SpringDemoService.model.Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
@Slf4j
public class MysqlRecordStorage {

    private MysqlDataSource mysqlDataSource;

    @Singular
    private Map<Class, ModelMapper> modelMappers;

    public List<Record> getRecords() {
        final String query = "SELECT * FROM demo.records";
        return executeQuery(query, Collections.emptyList(), Record.class);
    }

    public Record getRecordById(final String id) {
        final String query = "SELECT * FROM demo.records WHERE recordId=?";
        final List<Record> records = executeQuery(query, ImmutableList.of(id), Record.class);
        if (records.isEmpty()) {
            return null;
        } else {
            return records.get(0);
        }
    }

    public String createRecord(final Record record) {
        final String recordId = UUID.randomUUID().toString();
        final String query = "INSERT INTO demo.records(recordId, someProperty) VALUES(?, ?)";
        executeQuery(query, ImmutableList.of(recordId, record.getSomeProperty()), null);
        return recordId;
    }

    public void deleteRecordById(final String id) {
        final String query = "DELETE FROM demo.records WHERE recordId=?";
        executeQuery(query, ImmutableList.of(id), null);
    }

    public void updateRecordById(final Record record, final String id) {
        final String query = "UPDATE demo.records SET someProperty=? WHERE recordId=?";
        executeQuery(query, ImmutableList.of(record.getSomeProperty(), id), null);
    }

    private <T> List<T> executeQuery(final String queryTemplate,
                                     final List<Object> arguments,
                                     final Class<T> expectedClass) {
        try {
            final Connection connection = mysqlDataSource.getConnection();
            final PreparedStatement statement = connection.prepareStatement(queryTemplate);
            for (int i = 0; i < arguments.size(); i++) {
                final Object argument = arguments.get(i);
                if (argument.getClass() == Integer.class) {
                    statement.setInt(i + 1, (int) argument);
                } else {
                    statement.setString(i + 1, (String) argument);
                }
            }
            final List<T> result;
            if (expectedClass == null) {
                statement.executeUpdate();
                result = Collections.emptyList();
            } else {
                final ResultSet resultSet = statement.executeQuery();
                result = getFromResultSet(resultSet, expectedClass);
                resultSet.close();
            }
            statement.close();
            connection.close();
            return result;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    private <T> List<T> getFromResultSet(final ResultSet resultSet,
                                         final Class<T> expectedClass) {
        try {
            final List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                final ModelMapper<T> mapper = modelMappers.get(expectedClass);
                result.add(mapper.map(resultSet));
            }
            return result;
        } catch (final SQLException e) {
            log.error("Error Getting result from resultSet for class {}",
                    expectedClass.getName());
            throw new RuntimeException(e);
        }
    }
}
