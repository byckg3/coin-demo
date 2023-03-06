package demo.currency.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import demo.currency.controller.CoindeskController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CoindeskControllerTest
{
    private static CoindeskController coindesk;
    private static Map< String, String > codesToNames;


    @Test
    void testInputDateFormat()
    {
        // to do
    }
}
