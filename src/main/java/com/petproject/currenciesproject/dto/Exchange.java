package com.petproject.currenciesproject.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class Exchange {
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal convertedAmount;

    private Exchange() { }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public static Builder newBuilder(){
        return new Exchange().new Builder();
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

        public Builder setBaseCurrency(Currency baseCurrency){
            Exchange.this.setBaseCurrency(baseCurrency);
            return this;
        }

        public Builder setTargetCurrency(Currency targetCurrency){
            Exchange.this.setTargetCurrency(targetCurrency);
            return this;
        }

        public Builder setRate(BigDecimal rate){
            Exchange.this.setRate(rate);
            return this;
        }

        public Builder setAmmount(BigDecimal ammount){
            Exchange.this.setAmount(ammount);
            return this;
        }

        public Builder setConvertedAmount(BigDecimal convertedAmount){
            Exchange.this.setConvertedAmount(convertedAmount);
            return this;
        }

        public Exchange build(){
            return Exchange.this;
        }
    }
}
