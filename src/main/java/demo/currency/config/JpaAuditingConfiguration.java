package demo.currency.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing( dateTimeProviderRef = "utcDateTimeProvider" )
public class JpaAuditingConfiguration
{
    
}