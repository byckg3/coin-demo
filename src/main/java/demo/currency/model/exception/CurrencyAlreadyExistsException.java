package demo.currency.model.exception;

import lombok.Getter;

@Getter
public class CurrencyAlreadyExistsException extends RuntimeException
{
    private String currencyCode;
    private String name;

    public CurrencyAlreadyExistsException( String code, String name )
    {
        this.currencyCode = code;
        this.name = name;
    }
}
