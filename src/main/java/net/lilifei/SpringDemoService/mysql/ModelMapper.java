package net.lilifei.SpringDemoService.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ModelMapper<T> {

    T map(ResultSet resultSet) throws SQLException;
}
