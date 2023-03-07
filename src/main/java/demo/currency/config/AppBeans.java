package demo.currency.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.currency.model.Currency;
import demo.currency.model.service.CurrencyService;

@Configuration
public class AppBeans
{
    @Bean
	public Map< String, String > codeToNameMappings( CurrencyService currService )
	{
        Map< String, String > mappings = new HashMap<>();
        for ( Currency each: currService.getAll() )
        {
            mappings.put( each.getCode(), each.getName() );
        }
        return mappings;
	}
}