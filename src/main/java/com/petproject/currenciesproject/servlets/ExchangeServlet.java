package com.petproject.currenciesproject.servlets;

import com.petproject.currenciesproject.crud.CurrencyRepository;
import com.petproject.currenciesproject.crud.ExchangeRepository;
import com.petproject.currenciesproject.dto.Currency;
import com.petproject.currenciesproject.dto.Exchange;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@WebServlet(name = "ExchangeServlet", value = "/exchange")
public class ExchangeServlet extends HttpServlet {
    private final ExchangeRepository exchangeRepository = new ExchangeRepository();
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String baseCode = request.getParameter("from");
        String targetCode = request.getParameter("to");
        String reqAmount = request.getParameter("amount");

        if(baseCode == null || targetCode == null || reqAmount == null){
            response.sendError(400, "There are not filled fields!");
            return;
        }

        if (baseCode.equalsIgnoreCase(targetCode)){
            response.sendError(400, "Base and target currencies can't be same!");
            return;
        }

        if (!currencyRepository.isPresent(baseCode.toUpperCase()) || !currencyRepository.isPresent(targetCode.toUpperCase())){
            response.sendError(408, "There is no currency with such code!");
            return;
        }

        Currency base = currencyRepository.readByCode(baseCode);
        Currency target = currencyRepository.readByCode(targetCode);
        BigDecimal rate = getExchangeRateValue(base,target);
        if (rate == null){
            response.sendError(404, "Can't convert!");
            return;
        }
        BigDecimal amount = new BigDecimal(reqAmount);
        BigDecimal convertedAmount = amount.multiply(rate);

        Exchange exchange = Exchange.newBuilder()
                .setBaseCurrency(base)
                .setTargetCurrency(target)
                .setAmmount(amount)
                .setRate(rate)
                .setConvertedAmount(convertedAmount)
                .build();

        response.getWriter().println(exchange);
    }

    private BigDecimal getExchangeRateValue(Currency base, Currency target){
        String baseCode = base.getCode();
        String targetCode = target.getCode();

        if (exchangeRepository.isPresent(baseCode, targetCode)){
            return exchangeRepository.readByCode(baseCode.concat(targetCode)).getRate();

        } else if (exchangeRepository.isPresent(targetCode,baseCode)) {
            BigDecimal one = new BigDecimal(1);
            return one.divide(exchangeRepository.readByCode(targetCode.concat(baseCode)).getRate(),6, RoundingMode.CEILING);

        } else if (exchangeRepository.isPresent("USD", baseCode) &&
                    exchangeRepository.isPresent("USD", targetCode)){

            return exchangeRepository.readByCode("USD".concat(targetCode)).getRate()
                    .divide(exchangeRepository.readByCode("USD".concat(baseCode)).getRate(),6,RoundingMode.CEILING);
        } else return null;
    }
}
