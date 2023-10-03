package demo.currency.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class UtcDateTimeProviderTest
{
    @Test
    void givnOffsetDateTime_removeNanoSecondPrecision_shouldBeWithMillisecondPrecision()
    {
        var now = OffsetDateTime.now( ZoneOffset.UTC );
        var nanoseconds = 869750200;
        var milliseconds = 869;
        
        assertEquals( nanoseconds, now.withNano( nanoseconds ).getNano() );
        assertEquals( milliseconds * 1_000_000, now.withNano( milliseconds * 1_000_000 ).getNano() );
    }

    @Test
    void whenParseDateTimeString_thenParseSuccessfully( )
    {
        String dateTimeString = "2023-09-11T15:25:52.06+00";
        OffsetDateTime dateTime = OffsetDateTime.parse( dateTimeString );

        assertEquals( "2023-09-11T15:25:52.060Z", dateTime.toString() );
        assertDoesNotThrow( () -> OffsetDateTime.parse( "2023-09-11T15:25:52.060Z" ) );
        assertThrows( DateTimeParseException.class, () -> OffsetDateTime.parse( "2023-09-11 15:25:52.06+00" ) );
    }
}
