package com.petproject.currenciesproject.crud;
import org.sqlite.SQLiteDataSource;

public abstract class AbstractCRUD<T> implements CRUDInterface<T>{
    protected static final String dbUrl ="jdbc:sqlite:/home/atopiraka/IdeaProjects/CurrenciesProject/src/main/resources/data.db";

    //protected static final String dbUrl ="jdbc:sqlite:../resources/data.db";
    protected static SQLiteDataSource dataSource = new SQLiteDataSource();

}
