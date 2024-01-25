package org.example;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest {

    @Test
    public void testGetHistory() {
        String city = "stockholm";
        String date = "2024-01-24";
        JSONObject history = History.getHistory(city, date);
        String result = null;
        try {
            result = history.getJSONObject("location").getString("country");

        } catch (JSONException e){
            e.printStackTrace();
        }
        assertEquals("sweden", result.toLowerCase());
    }
    @Test
    public void testFormatHistory(){
        String cityONE = "stockholm";
        String cityTWO = "FAILING";
        String date = "2024-01-24";

        JSONObject testHistoryONE = History.getHistory(cityONE, date);
        JSONObject testHistoryTWO = History.getHistory(cityTWO, date); // Supposed to be null

        String resultONE = History.formatHistory(testHistoryONE, date); // Supposed to NOT be null


        assertNotNull(resultONE);
        assertEquals("2024-01-24", date.toLowerCase());
        assertNull(testHistoryTWO);
    }
}
