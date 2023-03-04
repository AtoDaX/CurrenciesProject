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

@WebServlet(name = "ExchangeRateServlet", urlPatterns = "/exchangerate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRepository exchangeRepository = new ExchangeRepository();
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod();
        if (!method.equals("PATCH")) {
            super.service(request, response);
        }

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
        }

        response.getWriter().println(exchangeRate);

    }


    protected void doPatch(HttpServletRequest request, HttpServletResponse response){

    }
}
