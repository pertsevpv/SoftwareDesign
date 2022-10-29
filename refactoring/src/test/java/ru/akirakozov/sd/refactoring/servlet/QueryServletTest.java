package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.formatter.HtmlProductFormatter;
import ru.akirakozov.sd.refactoring.formatter.Templates;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class QueryServletTest extends BaseServletTest<QueryServlet> {

  private HtmlProductFormatter formatter = new HtmlProductFormatter();

  @Override
  protected QueryServlet initServlet() {
    return new QueryServlet(productDao);
  }

  @Override
  protected ProductDao initDao() {
    return Mockito.mock(ProductDao.class);
  }

  @Test
  public void minQuery() throws SQLException, IOException {
    Mockito.when(request.getParameter(COMMAND_PARAM)).thenReturn(MIN);
    Mockito.when(productDao.min()).thenReturn(PRODUCT_LIST.get(0));

    servlet.doGet(request, response);

    assertThat(printWriter.toString())
      .isEqualToNormalizingWhitespace(formatter.formatMinMaxQuery(Templates.PRODUCT_MIN_TEMPLATE, PRODUCT_LIST.get(0)));
  }

  @Test
  public void maxQuery() throws SQLException, IOException {
    Mockito.when(request.getParameter(COMMAND_PARAM)).thenReturn(MAX);
    Mockito.when(productDao.max()).thenReturn(PRODUCT_LIST.get(2));

    servlet.doGet(request, response);

    assertThat(printWriter.toString())
      .isEqualToNormalizingWhitespace(formatter.formatMinMaxQuery(Templates.PRODUCT_MAX_TEMPLATE, PRODUCT_LIST.get(2)));
  }

  @Test
  public void countQuery() throws SQLException, IOException {
    Mockito.when(request.getParameter(COMMAND_PARAM)).thenReturn(COUNT);
    Mockito.when(productDao.count()).thenReturn((long) PRODUCT_LIST.size());

    servlet.doGet(request, response);

    assertThat(printWriter.toString())
      .isEqualToNormalizingWhitespace(formatter.formatNumberQuery(Templates.PRODUCT_COUNT_TEMPLATE, (long) PRODUCT_LIST.size()));
  }

  @Test
  public void sumQuery() throws SQLException, IOException {
    Mockito.when(request.getParameter(COMMAND_PARAM)).thenReturn(SUM);
    Mockito.when(productDao.sum()).thenReturn(PRICE_SUM);

    servlet.doGet(request, response);

    assertThat(printWriter.toString())
      .isEqualToNormalizingWhitespace(formatter.formatNumberQuery(Templates.PRODUCT_SUM_TEMPLATE, PRICE_SUM));
  }

}
