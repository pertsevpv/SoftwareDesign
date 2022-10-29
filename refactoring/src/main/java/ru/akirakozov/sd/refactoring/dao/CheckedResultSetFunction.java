package ru.akirakozov.sd.refactoring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface CheckedResultSetFunction<R> {
  R apply(ResultSet rs) throws SQLException;
}