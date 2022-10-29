package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import org.mockito.junit.MockitoJUnitRunner;
import ru.akirakozov.sd.refactoring.dao.ProductDao;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class AddProductServletTest extends BaseServletTest<AddProductServlet> {

  @Override
  protected AddProductServlet initServlet() {
    return new AddProductServlet(productDao);
  }

  @Override
  protected ProductDao initDao() {
    return new ProductDao();
  }

  @Test
  public void testAddOk() throws IOException {
    Mockito.when(request.getParameter(NAME_PARAM)).thenReturn(NAME1);
    Mockito.when(request.getParameter(PRICE_PARAM)).thenReturn(PRICE1);

    servlet.doGet(request, response);

    assertThat(printWriter.toString()).isEqualToNormalizingWhitespace(OK);
  }

  @Test
  public void testAddFail(){
    Mockito.when(request.getParameter(NAME_PARAM)).thenReturn(NAME1);
    Mockito.when(request.getParameter(PRICE_PARAM)).thenReturn(FAIL_PRICE);

    assertThrows(NumberFormatException.class, () -> servlet.doGet(request, response));
  }
}
