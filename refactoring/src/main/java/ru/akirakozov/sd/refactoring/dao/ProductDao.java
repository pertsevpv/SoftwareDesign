package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProductDao extends BaseDao<Product> {

  public Long sum() throws SQLException {
    String priceField = getFields().get(1);
    String query = String.format(Templates.SUM_TEMPLATE,
      priceField, getTable());

    return executeQuery(query, rs -> Long.valueOf(rs.getInt(1)));
  }

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
    Long price = Long.parseLong(map.get(fields.get(1)).toString());

    return new Product(name, price);
  }

}
