package demo.currency.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties( prefix = "app.props" )
public class AppProperties
{
    private Integer pageSize;
}