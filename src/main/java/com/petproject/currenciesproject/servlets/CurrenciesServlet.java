package com.petproject.currenciesproject.servlets;
import com.petproject.currenciesproject.crud.CurrencyRepository;
import com.petproject.currenciesproject.dto.Currency;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "currenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final CurrencyRepository crud = new CurrencyRepository();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");

        List<Currency> currencyList = crud.readAll();
        if (currencyList == null) {
            response.sendError(500, "Cant get access to database");
            return;
        }

        response.getWriter().println(currencyList);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String reqName = request.getParameter("name");
        String reqCode = request.getParameter("code");
        String reqSign = request.getParameter("sign");
        if(reqSign==null || reqCode==null || reqName == null){
            response.sendError(400, "There are not filled fields!");
        }

        Currency toInsert = Currency.newBuilder()
                .setName(reqName)
                .setCode(reqCode)
                .setSign(reqSign)
                .build();

        boolean dbAnswer = crud.create(toInsert);
        if (!dbAnswer){
            response.sendError(409, "Currency with such code already exists!");
        }



    }

}