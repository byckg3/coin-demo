package demo.currency.config;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

@Component( "utcDateTimeProvider" )
public class UtcDateTimeProvider implements DateTimeProvider
{
    @Override
    public Optional<TemporalAccessor> getNow()
    {
        var now = OffsetDateTime.now( ZoneOffset.UTC );
        var nowWithMillisecondPrecision = now.withNano( ( now.getNano() / 1_000_000 ) * 1_000_000 );

        return Optional.of( nowWithMillisecondPrecision );
    }
}
