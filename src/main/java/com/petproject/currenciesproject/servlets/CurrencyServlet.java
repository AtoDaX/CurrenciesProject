package com.petproject.currenciesproject.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petproject.currenciesproject.crud.AbstractCRUD;
import com.petproject.currenciesproject.crud.CurrencyRepository;
import com.petproject.currenciesproject.dto.Currency;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "currencyServlet", urlPatterns = "/currency/*")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyRepository crud = new CurrencyRepository();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        String code = request.getPathInfo().replaceAll("/", "").toUpperCase();

        if (code.length() != 3) {
            response.sendError(400, "Incorrect currency code");
            return;
        }

        Currency currency = crud.readByCode(code);
        if (currency == null) {
            response.sendError(404, "No currency with this code");
            return;
        }

        response.getWriter().println(currency);
    }

}
