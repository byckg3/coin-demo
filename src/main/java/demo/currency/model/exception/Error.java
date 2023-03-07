package demo.currency.model.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class Error
{
    private Map< String, Object > data;

    public Error( String msg )
    {
        data = new HashMap<>();
        data.put("message", msg );
    }
}
