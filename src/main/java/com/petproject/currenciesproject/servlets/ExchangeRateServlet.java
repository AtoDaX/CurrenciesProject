package com.petproject.currenciesproject.servlets;

import com.petproject.currenciesproject.crud.CurrencyRepository;
import com.petproject.currenciesproject.crud.ExchangeRepository;
import com.petproject.currenciesproject.dto.Currency;
import com.petproject.currenciesproject.dto.ExchangeRate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "ExchangeRateServlet", urlPatterns = "/exchangerate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRepository exchangeRepository = new ExchangeRepository();
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod();
        System.out.println(method);
        if (!method.equals("PATCH")) {
            super.service(request, response);
            return;
        }
        System.out.println(method);
        this.doPatch(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currencyCode = request.getPathInfo().replaceAll("/", "").toUpperCase();

        if (currencyCode.length() != 6) {
            response.sendError(400, "Incorrect currency code");
            return;
        }

        ExchangeRate exchangeRate = exchangeRepository.readByCode(currencyCode);

        if (exchangeRate == null){
            response.sendError(404, "No exchange rate with such code");
            return;
        }

        response.getWriter().println(exchangeRate);

    }


    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currencyCode = request.getPathInfo().replaceAll("/", "").toUpperCase();
        if (currencyCode.length() != 6) {
            response.sendError(400, "Incorrect currency code");
            return;
        }
        String reqRate = request.getParameter("rate");
        if (reqRate == null){
            response.sendError(400,"There are not filled field!");
            return;
        }

        BigDecimal newRate = new BigDecimal(reqRate);
        ExchangeRate exchangeRate = exchangeRepository.readByCode(currencyCode);
        if (exchangeRate == null){
            response.sendError(404, "No exchange rate with such code");
            return;
        }
        exchangeRate.setRate(newRate);
        exchangeRepository.update(exchangeRate);

    }
}
