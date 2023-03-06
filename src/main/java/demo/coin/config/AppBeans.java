package demo.coin.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.coin.model.CoinService;

@Configuration
public class AppBeans
{
    @Bean
	public Map< String, String > codeToNameMappings( CoinService coinService )
	{
        return coinService.codeToNameMappings();
	}
}