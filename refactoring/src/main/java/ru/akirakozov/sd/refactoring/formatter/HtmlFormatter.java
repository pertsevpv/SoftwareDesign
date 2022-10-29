package ru.akirakozov.sd.refactoring.formatter;

import java.util.List;

public abstract class HtmlFormatter<T> {

  public abstract String formatGet(List<T> list);
  public abstract String formatLine(T obj);
  public abstract String formatMinMaxQuery(String template, T obj);
  public abstract String formatNumberQuery(String template, Long num);
}
