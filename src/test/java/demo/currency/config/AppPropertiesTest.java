package demo.currency.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppPropertiesTest
{
    @Autowired
    private AppProperties appProps;

    @Test
    void whenSetValueForPageSize_thenPropertyExists()
    {
       assertTrue( appProps.getPageSize() > 0 );
    }
}
