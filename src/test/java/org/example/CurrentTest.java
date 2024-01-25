package org.example;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CurrentTest {

    @Test
    public void testGetCurrent() {
        try {
            String city = "stockholm";
            String countryName = Current.getCurrent(city).getJSONObject("location").getString("country");
            assertEquals("sweden", countryName.toLowerCase());

            city = "stockholm";
            countryName = Current.getCurrent(city).getJSONObject("location").getString("country");
            assertEquals("sweden", countryName.toLowerCase());

            city = "london";
            countryName = Current.getCurrent(city).getJSONObject("location").getString("country");
            assertEquals("united kingdom", countryName.toLowerCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testFormatCurrent() {
        String city = "stockholm";
        JSONObject jsonResponse = Current.getCurrent(city);
        String message = Current.formatCurrent(jsonResponse);
        assertNotNull(message);
    }
}
