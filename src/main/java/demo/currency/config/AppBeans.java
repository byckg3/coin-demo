package demo.currency.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.currency.model.CurrencyService;

@Configuration
public class AppBeans
{
    @Bean
	public Map< String, String > codeToNameMappings( CurrencyService currService )
	{
        return currService.codeToNameMappings();
	}
}