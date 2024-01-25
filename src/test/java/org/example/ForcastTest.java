package org.example;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ForcastTest {


    @Test
    public void testGetForecast() {
        String city = "stockholm";
        String cityTWO = "FAILING";
        String days = "2";

        JSONObject forecast = Forecast.getForecast(city, days);
        JSONObject testForecastTWO = Forecast.getForecast(cityTWO, days); // Supposed to be null

        String result = null;
        try {
            result = forecast.getJSONObject("location").getString("country");

        } catch (JSONException e){
            e.printStackTrace();
        }

        assertEquals("sweden", result.toLowerCase());
        assertNull(testForecastTWO);
    }
    @Test
    public void testFormatForcast(){
        String cityONE = "stockholm";

        String days = "2";

        JSONObject testForecastONE = Forecast.getForecast(cityONE, days);


        String resultONE = Forecast.formatForecast(testForecastONE); // Supposed to NOT be null

        String date = resultONE.substring(0, resultONE.indexOf(':')); // Supposed to contain a string that is = "date"

        assertNotNull(resultONE);
        assertEquals("date", date.toLowerCase());
    }
}
