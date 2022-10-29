package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.entity.Product;

import java.util.List;
import java.util.Map;

public class ProductDao extends BaseDao<Product> {

  @Override
  protected String getTable() {
    return "PRODUCT";
  }

  @Override
  protected List<String> getFields() {
    return List.of("NAME", "PRICE");
  }

  @Override
  protected List<String> getValues(Product entity) {
    String name = entity.getName();
    String price = String.valueOf(entity.getPrice());

    return List.of(name, price);
  }

  @Override
  protected Product doTransform(Map<String, Object> map) {
    List<String> fields = getFields();

    String name = (String) map.get(fields.get(0));
    Long price = (Long) map.get(fields.get(1));

    return new Product(name, price);
  }

}
