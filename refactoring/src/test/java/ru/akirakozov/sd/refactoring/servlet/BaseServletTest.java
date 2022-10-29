package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.entity.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public abstract class BaseServletTest<S extends HttpServlet> {

  protected S servlet;
  protected PrintWriter printWriter;
  protected ProductDao productDao;
  @Mock
  protected HttpServletRequest request;
  @Mock
  protected HttpServletResponse response;

  protected static final String NAME_PARAM = "name";
  protected static final String PRICE_PARAM = "price";
  protected static final String NAME1 = "NAME1";
  protected static final String PRICE1 = "100";
  protected static final String NAME2 = "NAME2";
  protected static final String PRICE2 = "200";
  protected static final String NAME3 = "NAME3";
  protected static final String PRICE3 = "300";
  protected static final String FAIL_PRICE = "ABACABA";
  protected static final String OK = "OK";
  protected static final List<Product> EMPTY_LIST = List.of();
  protected static final List<Product> PRODUCT_LIST = List.of(
    new Product(NAME1, Long.parseLong(PRICE1)),
    new Product(NAME2, Long.parseLong(PRICE2)),
    new Product(NAME3, Long.parseLong(PRICE3))
  );

  @Before
  public void before() throws IOException {
    productDao = initDao();
    servlet = initServlet();

    printWriter = new TestWriter();
    Mockito.when(response.getWriter()).thenReturn(printWriter);
  }

  protected abstract S initServlet();
  protected abstract ProductDao initDao();

}
