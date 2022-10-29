package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.formatter.HtmlProductFormatter;
import ru.akirakozov.sd.refactoring.formatter.Templates;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private final ProductDao dao;
    private final HtmlProductFormatter formatter = new HtmlProductFormatter();

    public QueryServlet(ProductDao dao){
        this.dao = dao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        try {
            String formatted = switch (command){
                case "max" -> formatter.formatMinMaxQuery(Templates.PRODUCT_MAX_TEMPLATE, dao.max());
                case "min" -> formatter.formatMinMaxQuery(Templates.PRODUCT_MIN_TEMPLATE, dao.min());
                case "count" -> formatter.formatNumberQuery(Templates.PRODUCT_COUNT_TEMPLATE, dao.count());
                case "sum" -> formatter.formatNumberQuery(Templates.PRODUCT_SUM_TEMPLATE, dao.sum());
                default -> "Unknown command: " + command;
            };
            response.getWriter().println(formatted);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
