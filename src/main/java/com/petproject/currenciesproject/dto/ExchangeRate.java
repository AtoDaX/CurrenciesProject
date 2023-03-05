package com.petproject.currenciesproject.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class ExchangeRate {
    private long id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;

    private ExchangeRate() { };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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
    public static Builder newBuilder(){
        return new ExchangeRate().new Builder();
    }

    public class Builder{
        private Builder() { }

        public Builder setId(Long id){
            ExchangeRate.this.id = id;
            return this;
        }

        public Builder setBaseCurrency(Currency baseCurrency){
            ExchangeRate.this.baseCurrency = baseCurrency;
            return this;
        }

        public Builder setTargetCurrency (Currency targetCurrency){
            ExchangeRate.this.targetCurrency = targetCurrency;
            return this;
        }

        public Builder setRate (BigDecimal rate){
            ExchangeRate.this.rate = rate;
            return this;
        }

        public ExchangeRate build(){
            return ExchangeRate.this;
        }
    }

}
