package com.petproject.currenciesproject.servlets;

import com.petproject.currenciesproject.crud.CurrencyRepository;
import com.petproject.currenciesproject.crud.ExchangeRepository;
import com.petproject.currenciesproject.dto.Currency;
import com.petproject.currenciesproject.dto.ExchangeRate;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "ExchangeRatesServlet", value = "/exchangerates")
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRepository exchangeRepository = new ExchangeRepository();
    private final CurrencyRepository currencyRepository = new CurrencyRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        List<ExchangeRate> exchangeRateList = exchangeRepository.readAll();
        if (exchangeRateList==null){
            response.sendError(500, "Cant get access to database");
            return;
        }
        response.getWriter().println(exchangeRateList);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String reqBaseCode = request.getParameter("baseCurrencyCode");
        String reqTargetCode = request.getParameter("targetCurrencyCode");
        String reqRate = request.getParameter("rate");

        if (reqBaseCode==null || reqTargetCode == null || reqRate == null){
            response.sendError(400, "There are not filled fields!");
            return;
        }

        if (reqBaseCode.equalsIgnoreCase(reqTargetCode)){
            response.sendError(400, "Base and target currencies can't be same!");
            return;
        }

        if (!currencyRepository.isPresent(reqBaseCode) || !currencyRepository.isPresent(reqTargetCode)){
            response.sendError(408, "There is no currency with such code!");
            return;
        }

        Currency base = currencyRepository.readByCode(reqBaseCode);
        Currency target = currencyRepository.readByCode(reqTargetCode);
        BigDecimal rate = new BigDecimal(reqRate);

        ExchangeRate toInsert = ExchangeRate.newBuilder()
                .setBaseCurrency(base)
                .setTargetCurrency(target)
                .setRate(rate)
                .build();

        boolean dbAnswer = exchangeRepository.create(toInsert);
        if(!dbAnswer){
            response.sendError(409,"Exchange rate with these parameters is already exists!");
        }


    }
}
