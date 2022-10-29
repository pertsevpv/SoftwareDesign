package ru.akirakozov.sd.refactoring.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseDao<T> {

  private static final String TEST_DB_PATH = "jdbc:sqlite:test.db";

  public void insert(T entity) throws SQLException {
    String query = String.format(Templates.INSERT_TEMPLATE,
      getTable(), joinFields(), joinValues(entity)
    );

    executeInsertQuery(query);
  }

  protected ResultSet executeQuery(String query) throws SQLException {
    try (Connection c = DriverManager.getConnection(TEST_DB_PATH)) {
      try (Statement statement = c.createStatement()) {
        return statement.executeQuery(query);
      }
    }
  }

  protected void executeInsertQuery(String query) throws SQLException {
    try (Connection c = DriverManager.getConnection(TEST_DB_PATH)) {
      try (Statement statement = c.createStatement()) {
        statement.executeUpdate(query);
      }
    }
  }

  protected T transform(ResultSet set) throws SQLException {
    Map<String, Object> map = new HashMap<>();

    for (String field : getFields()) {
      Object value = set.getObject(field);
      map.put(field, value);
    }

    return doTransform(map);
  }

  private String joinFields(){
    return String.join(", ", getFields());
  }

  private String joinValues(T entity){
    return getValues(entity).stream()
      .map(value -> "\"" + value + "\"")
      .collect(Collectors.joining(", "));
  }

  protected abstract String getTable();

  protected abstract List<String> getFields();

  protected abstract List<String> getValues(T entity);

  protected abstract T doTransform(Map<String, Object> map);
}
