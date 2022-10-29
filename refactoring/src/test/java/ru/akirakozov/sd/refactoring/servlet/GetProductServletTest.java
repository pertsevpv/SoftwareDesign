package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.formatter.HtmlProductFormatter;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GetProductServletTest extends BaseServletTest<GetProductsServlet> {

  private HtmlProductFormatter formatter = new HtmlProductFormatter();

  @Override
  protected GetProductsServlet initServlet() {
    return new GetProductsServlet(productDao);
  }

  @Override
  protected ProductDao initDao() {
    return Mockito.mock(ProductDao.class);
  }

  @Test
  public void testEmpty() throws SQLException, IOException {
    Mockito.when(productDao.selectAll()).thenReturn(EMPTY_LIST);

    servlet.doGet(request, response);
    assertThat(printWriter.toString())
      .isEqualToNormalizingWhitespace(formatter.formatGet(EMPTY_LIST));
  }

  @Test
  public void testMany() throws SQLException, IOException {
    Mockito.when(productDao.selectAll()).thenReturn(PRODUCT_LIST);

    servlet.doGet(request, response);
    assertThat(printWriter.toString())
      .isEqualToNormalizingWhitespace(formatter.formatGet(PRODUCT_LIST));
  }

}
