package net.lilifei.SpringDemoService.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.AllArgsConstructor;
import net.lilifei.SpringDemoService.model.Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class MysqlRecordStorage {

    final MysqlDataSource mysqlDataSource;

    public Record getRecordById(final int id) {
        try {
            final String query = "SELECT * FROM demo.records WHERE id=?";
            final Connection connection = mysqlDataSource.getConnection();
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            final ResultSet resultSet = statement.executeQuery();
            final Record record;
            if (resultSet.next()) {
                record = Record.builder()
                        .someProperty(resultSet.getString(2))
                        .build();
            } else {
                record = null;
            }
            // Close
            resultSet.close();
            statement.close();
            connection.close();
            return record;
        } catch (final SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
