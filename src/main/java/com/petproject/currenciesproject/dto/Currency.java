package com.petproject.currenciesproject.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Currency {
    private Long id;
    private String name;
    private String code;
    private String sign;

    /*public Currency(long id, String name, String code, String sign) {
        super(id);
        this.name = name;
        this.code = code;
        this.sign = sign;
    }
    public Currency(String name, String code, String sign) {
        this.name = name;
        this.code = code;
        this.sign = sign;
    }*/

    private Currency(){ }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    public static Builder newBuilder() {
        return new Currency().new Builder();
    }

    @Override
    public String toString(){
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public class Builder{
        private Builder() { }

        public Builder setCurrencyId(Long id){
            Currency.this.id = id;
            return this;
        }

        public Builder setName(String name){
            Currency.this.name = name;
            return this;
        }

        public Builder setCode(String code){
            Currency.this.code = code;
            return this;
        }

        public Builder setSign(String sign){
            Currency.this.sign = sign;
            return this;
        }

        public Currency build(){
            return Currency.this;
        }
    }

}
