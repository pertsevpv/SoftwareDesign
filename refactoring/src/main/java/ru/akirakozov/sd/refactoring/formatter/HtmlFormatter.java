package ru.akirakozov.sd.refactoring.formatter;

import java.util.List;

public abstract class HtmlFormatter<T> {

  public abstract String formatGet(List<T> list);

}
