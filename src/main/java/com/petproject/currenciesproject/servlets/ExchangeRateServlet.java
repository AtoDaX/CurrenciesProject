package com.petproject.currenciesproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ExchangeRateServlet", urlPatterns = "/exchangerate/*")
public class ExchangeRateServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod();
        if (!method.equals("PATCH")) {
            super.service(request, response);
        }

        this.doPatch(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){

    }


    protected void doPatch(HttpServletRequest request, HttpServletResponse response){

    }
}
