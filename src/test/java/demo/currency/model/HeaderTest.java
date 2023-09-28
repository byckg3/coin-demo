package demo.currency.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class HeaderTest
{
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
