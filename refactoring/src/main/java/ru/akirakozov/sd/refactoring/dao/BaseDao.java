package ru.akirakozov.sd.refactoring.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseDao<T> {

  private static final String TEST_DB_PATH = "jdbc:sqlite:test.db";

  public void createTable() throws SQLException {
    String joinedFields = String.join(", ", getFieldsDefinitions());
    String query = String.format(Templates.CREATE_TABLE_TEMPLATE,
      getTable(), joinedFields);

    executeUpdateQuery(query);
  }

  public List<T> selectAll() throws SQLException {
    String query = String.format(Templates.SELECT_ALL_TEMPLATE,
      getTable()
    );

    return executeQuery(query, rs -> {
      List<T> result = new ArrayList<>();
      while (rs.next())
        result.add(transform(rs));

      return result;
    });
  }

  public void insert(T entity) throws SQLException {
    String query = String.format(Templates.INSERT_TEMPLATE,
      getTable(), joinFields(), joinValues(entity)
    );

    executeUpdateQuery(query);
  }

  public T max() throws SQLException {
    String query = String.format(Templates.MAX_TEMPLATE,
      getTable());

    return executeQuery(query, this::transform);
  }

  public T min() throws SQLException {
    String query = String.format(Templates.MIN_TEMPLATE,
      getTable());

    return executeQuery(query, this::transform);
  }

  public Long count() throws SQLException {
    String query = String.format(Templates.COUNT_TEMPLATE,
      getTable());

    return executeQuery(query, rs -> Long.valueOf(rs.getInt(1)));
  }

  protected <R> R executeQuery(String query, CheckedResultSetFunction<R> function) throws SQLException {
    try (Connection c = DriverManager.getConnection(TEST_DB_PATH)) {
      try (Statement statement = c.createStatement()) {
        ResultSet rs = statement.executeQuery(query);
        return function.apply(rs);
      }
    }
  }

  protected void executeUpdateQuery(String query) throws SQLException {
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

  private String joinFields() {
    return String.join(", ", getFields());
  }

  private String joinValues(T entity) {
    return getValues(entity).stream()
      .map(value -> "\"" + value + "\"")
      .collect(Collectors.joining(", "));
  }

  protected abstract String getTable();

  protected abstract List<String> getFields();

  protected abstract List<String> getFieldsDefinitions();

  protected abstract List<String> getValues(T entity);

  protected abstract T doTransform(Map<String, Object> map);
}
