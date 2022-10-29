package ru.akirakozov.sd.refactoring.dao;

public class Templates {

  public static String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
  public static String SELECT_ALL_TEMPLATE = "SELECT * FROM %s";
  public static String MAX_TEMPLATE = "SELECT * FROM %s ORDER BY PRICE DESC LIMIT 1";
  public static String MIN_TEMPLATE = "SELECT * FROM %s ORDER BY PRICE LIMIT 1";
  public static String SUM_TEMPLATE = "SELECT SUM(%s) FROM %s";
  public static String COUNT_TEMPLATE = "SELECT COUNT(*) FROM %s";
}
