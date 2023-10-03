package demo.currency.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Validated
@ConfigurationProperties( prefix = "app.props" )
public class AppProperties
{
    @NotNull( message = "page size must be not null" )
    @Positive( message = "page size must be positive integer" )
    private Integer pageSize;
}