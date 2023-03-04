package com.petproject.currenciesproject.utils;

public class Utils {
    public static <T> boolean  isLong(T value){
        try {
            Long.parseLong(String.valueOf(value));
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
