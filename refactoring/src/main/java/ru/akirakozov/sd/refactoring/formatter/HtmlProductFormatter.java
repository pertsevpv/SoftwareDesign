package ru.akirakozov.sd.refactoring.formatter;

import ru.akirakozov.sd.refactoring.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class HtmlProductFormatter extends HtmlFormatter<Product> {

  @Override
  public String formatGet(List<Product> list) {
    String lines = list.stream()
      .map(product -> String.format(Templates.PRODUCT_LINE_TEMPLATE, product.getName(), product.getPrice()))
      .collect(Collectors.joining("\n"));
    return String.format(Templates.BASE_HTML_TEMPLATE, lines);
  }

}
