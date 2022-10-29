package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class BaseServletTest<S extends HttpServlet> {

  protected S servlet;
  protected PrintWriter printWriter;
  @Mock
  protected HttpServletRequest request;
  @Mock
  protected HttpServletResponse response;

  protected static final String NAME_PARAM = "name";
  protected static final String PRICE_PARAM = "price";
  protected static final String NAME1 = "NAME1";
  protected static final String PRICE1 = "100";
  protected static final String FAIL_PRICE = "ABACABA";
  protected static final String OK = "OK";

  @Before
  public void before() throws IOException {
    servlet = initServlet();

    printWriter = new TestWriter();
    Mockito.when(response.getWriter()).thenReturn(printWriter);
  }

  protected abstract S initServlet();

}
