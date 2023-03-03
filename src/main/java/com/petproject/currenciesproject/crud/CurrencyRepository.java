package com.petproject.currenciesproject.crud;

import com.petproject.currenciesproject.dto.Currency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRepository extends AbstractCRUD<Currency>{

    private Currency generateCurrency(ResultSet resultSet){
        Currency toReturn;
        try {
            toReturn = Currency.newBuilder()
                    .setCurrencyId((long) resultSet.getInt("id"))
                    .setCode(resultSet.getString("code"))
                    .setName(resultSet.getString("fullName"))
                    .setSign(resultSet.getString("sign"))
                    .build();
            if (toReturn.getId()==0){
                toReturn = null;
            }
            return toReturn;
        } catch (SQLException e) {
            return null;
        }
    }

    public Currency readByCode(String code){
        Currency toReturn;
        String statement = "SELECT * FROM currencies WHERE code = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setString(1,code);
            ResultSet resultSet = preparedStatement.executeQuery();
            toReturn = generateCurrency(resultSet);
            return toReturn;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Currency readById(Long id) {
        Currency toReturn;
        String statement = "SELECT * FROM currencies WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            toReturn = generateCurrency(resultSet);
            return toReturn;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Currency> readAll() {
        List<Currency> toReturn = new ArrayList<>();
        String stringStatement = "SELECT * FROM currencies";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(stringStatement);
            while (resultSet.next()){
                toReturn.add(generateCurrency(resultSet));
            }
            return toReturn;
        } catch (SQLException e) {
            return null;
        }
    }



    @Override
    public boolean create(Currency entity) {
        String statement =
                "INSERT INTO currencies (code, fullName, sign) " +
                "VALUES(?, ?, ?)";

        if (isPresent(entity.getCode())){
            return false;
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setString(1,entity.getCode());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3,entity.getSign());
            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void update(Currency entity) {

    }

    @Override
    public void delete(Long id) {

    }

    public boolean isPresent(String value){
        String statement = "SELECT EXISTS(SELECT * FROM currencies WHERE code = ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setString(1,value);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            return false;
        }

    }
}
