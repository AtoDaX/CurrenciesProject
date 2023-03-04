package com.petproject.currenciesproject.crud;

import com.petproject.currenciesproject.dto.Currency;
import com.petproject.currenciesproject.dto.ExchangeRate;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRepository extends AbstractCRUD<ExchangeRate> {
    protected Connection connection;

    {
        try {
            dataSource.setUrl(dbUrl);
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    CurrencyRepository currencies = new CurrencyRepository();
    private ExchangeRate generateExchangeRate(ResultSet resultSet){

        try{
            ExchangeRate toReturn = ExchangeRate.newBuilder()
                    .setId(resultSet.getLong("id"))
                    .setBaseCurrency(currencies.readById(resultSet.getLong("baseCurrencyId")))
                    .setTargetCurrency(currencies.readById(resultSet.getLong("targetCurrencyId")))
                    .setRate(resultSet.getBigDecimal("rate"))
                    .build();
            return toReturn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ExchangeRate readById(Long id) {
        return null;
    }

    @Override
    public ExchangeRate readByCode(String code) {
        ExchangeRate toReturn;
        String statement = "SELECT * FROM exchangeRates WHERE baseCurrencyId=? AND targetCurrencyId=?";
        String baseCurrencyCode = code.substring(0,3);
        String targetCurrencyCode = code.substring(3,6);
        Currency base = currencies.readByCode(baseCurrencyCode);
        Currency target = currencies.readByCode(targetCurrencyCode);
        if (base == null || target == null){
            return null;
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setLong(1, base.getId());
            preparedStatement.setLong(2, target.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            toReturn = generateExchangeRate(resultSet);
            return toReturn;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<ExchangeRate> readAll() {
        List<ExchangeRate> toReturn = new ArrayList<>();
        String stringStatement = "SELECT * FROM exchangeRates";

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(stringStatement);
            while (resultSet.next()){
                toReturn.add(generateExchangeRate(resultSet));
            }
            return toReturn;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean create(ExchangeRate entity) {
        if (isPresent(entity)){
            return false;
        }


        String stringStatement =
                "INSERT INTO exchangeRates " +
                "(baseCurrencyId, targetCurrencyId, rate) " +
                "VALUES(?, ?, ?)";
        Long base = entity.getBaseCurrency().getId();
        Long target = entity.getTargetCurrency().getId();
        BigDecimal rate = entity.getRate();

        try(PreparedStatement preparedStatement = connection.prepareStatement(stringStatement)) {

            preparedStatement.setLong(1,base);
            preparedStatement.setLong(2,target);
            preparedStatement.setBigDecimal(3,rate);
            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean isPresent(ExchangeRate entity){
        String statement = "SELECT EXISTS(SELECT * FROM exchangeRates WHERE baseCurrencyId=? AND targetCurrencyId=?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setLong(1,entity.getBaseCurrency().getId());
            preparedStatement.setLong(2,entity.getTargetCurrency().getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            return false;
        }
    }
}
