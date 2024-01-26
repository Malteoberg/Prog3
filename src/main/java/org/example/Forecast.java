package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Represents a class for retrieving and formatting weather forecasts using a third-party API.
 */
public class Forecast {
    private static final String api_key = "b52bdef2c17941168aa104617240501";
    private static final String base_url = "http://api.weatherapi.com/v1/forecast.json";

    /**
     * Requests user input for the city and the number of days to forecast. Retrieves the forecast data,
     * formats it, and displays the information using JOptionPane.
     */
    public void forecast() {
        String city = JOptionPane.showInputDialog(null, "For which city do you want the forecast for? ", "Weather forecast", JOptionPane.QUESTION_MESSAGE);
        String days = JOptionPane.showInputDialog(null, "How many days ahead do you want the forecast? (1-3 including today) ", "Weather forecast", JOptionPane.QUESTION_MESSAGE);

        JSONObject forecast = getForecast(city, days);
        String formattedForecast = formatForecast(forecast);
        // Visa det sammanfogade meddelandet i en enda JOptionPane
        if (formattedForecast != null)
            JOptionPane.showMessageDialog(null, formattedForecast, " Weather information", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Retrieves the weather forecast data for a specified city and number of days from the weather API.
     *
     * @param city The city for which the forecast is requested.
     * @param days The number of days to forecast (1-3 including today).
     * @return A JSONObject containing the forecast data.
     */
    public static JSONObject getForecast(String city, String days){
        // Sätter ihop url och skapar ny URL objekt
        JSONObject forcast = null;
        String api_url = base_url + "?key=" + api_key + "&q=" + city + "&days=" + days;
        URL url = null;
        try {
            url = new URL(api_url);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        // Öppnar connection till url och sätter metoden till GET
        if (url != null) {

            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Hämtar respons koden (om 200 är det ok och koden körs vid if)
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Läser responsen
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Parse responsen till json objekt
                    forcast = new JSONObject(response.toString());
                }
            } catch(IOException e){
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
        return forcast;
    }

    /**
     * Formats the weather forecast data into a human-readable string.
     *
     * @param forecast A JSONObject containing the forecast data.
     * @return A formatted string containing weather information for each forecasted day.
     */
    public static String formatForecast(JSONObject forcast) {
        // Hämtar data för att visa
        StringBuilder messageBuilder = null;
        if (forcast == null ) return null;
        try {
            JSONObject location_data = forcast.getJSONObject("location");
            String city_name = location_data.getString("name");
            String country_name = location_data.getString("country");


            JSONObject forecast_data = forcast.getJSONObject("forecast");
            JSONArray forecastDayArray = forecast_data.getJSONArray("forecastday");

            // Lägg till en StringBuilder för att bygga upp meddelandet
            messageBuilder = new StringBuilder();
            for (int i = 0; i < forecastDayArray.length(); i++) {
                JSONObject forecastDayObject = forecastDayArray.getJSONObject(i);

                String date = forecastDayObject.getString("date");

                double avgTempC = forecastDayObject.getJSONObject("day").getDouble("avgtemp_c");
                int rain = forecastDayObject.getJSONObject("day").getInt("daily_chance_of_rain");

                String chance_of_rain = rain + "%";

                // Bygg upp meddelandet för varje dag

                messageBuilder.append("Date: ").append(date).append("\n")
                        .append("Country: ").append(country_name).append("\n")
                        .append("City: ").append(city_name).append("\n")
                        .append("Temperature (Celsius): ").append(avgTempC).append("\n")
                        .append("Chance of rain: ").append(chance_of_rain).append("\n\n");
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return messageBuilder.toString();
    }
}