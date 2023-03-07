package demo.currency.model.exception;

import lombok.Getter;

@Getter
public class CurrencyNotFoundException extends RuntimeException
{
    private String currencyCode;

    public CurrencyNotFoundException( String code ) {
        this.currencyCode = code;
    }
}