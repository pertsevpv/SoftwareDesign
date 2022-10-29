package ru.akirakozov.sd.refactoring.formatter;

import ru.akirakozov.sd.refactoring.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class HtmlProductFormatter extends HtmlFormatter<Product> {

  @Override
  public String formatGet(List<Product> list) {
    String lines = list.stream()
      .map(this::formatLine)
      .collect(Collectors.joining("\n"));
    return String.format(Templates.BASE_HTML_TEMPLATE, lines);
  }

  @Override
  public String formatLine(Product product) {
    return String.format(Templates.PRODUCT_LINE_TEMPLATE, product.getName(), product.getPrice());
  }

  @Override
  public String formatMinMaxQuery(String template, Product obj) {
    return String.format(Templates.BASE_HTML_TEMPLATE,
      String.format(template, formatLine(obj))
    );
  }

  @Override
  public String formatNumberQuery(String template, Long num) {
    return String.format(Templates.BASE_HTML_TEMPLATE,
      String.format(template, num)
    );
  }

}
