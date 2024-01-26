package org.example;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a class for retrieving and formatting current weather data using a third-party API.
 */
public class Current {

    private static final String API_KEY = "b52bdef2c17941168aa104617240501";

    /**
     * Requests user input for the city, retrieves the current weather data, formats it,
     * and displays the information using JOptionPane.
     */
    public void current() {
        //Ber användaren om stad och land med hjälp av JOptionPane
        String city = JOptionPane.showInputDialog(null, "Enter city: ", "Weather information", JOptionPane.QUESTION_MESSAGE);

        JSONObject current = getCurrent(city);
        String formattedCurrent = formatCurrent(current);
        if (formattedCurrent != null)
            JOptionPane.showMessageDialog(null, formattedCurrent, " Weather information", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Retrieves current weather data for a specified city from the weather API.
     *
     * @param city The city for which current weather data is requested.
     * @return A JSONObject containing the current weather data.
     */
    public static JSONObject getCurrent(String city) {
        JSONObject jsonResponse = null;
        try {

            String urlString = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + city;

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Skapa ett JSON-objekt från API-svaret
                jsonResponse = new JSONObject(response.toString());
            }
            else {
                String message = "Invalid city!";
                JOptionPane.showMessageDialog(null,message," Weather information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return jsonResponse;
    }

    /**
     * Formats current weather data into a human-readable string.
     *
     * @param jsonResponse A JSONObject containing the current weather data.
     * @return A formatted string containing current weather information.
     */
    public static String formatCurrent(JSONObject jsonResponse){
        String message = null;
        if (jsonResponse == null) return message;
        try {
            // Extrahera data från JSON-objektet
            JSONObject location = jsonResponse.getJSONObject("location");
            JSONObject current = jsonResponse.getJSONObject("current");

            // Hämta specifik information
            double lat = location.getDouble("lat");
            double lon = location.getDouble("lon");
            double tempC = current.getDouble("temp_c");
            double windKph = current.getDouble("wind_kph");
            int humidity = current.getInt("humidity");
            String country_name = location.getString("country");
            String city_name = location.getString("name");
            String conditionText = current.getJSONObject("condition").getString("text");

            //Hämta dagens datum
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);

            // Skapar en meddelande ruta med hjälp av Joptionpane
            message = "Country: " + country_name + "\n" +
                    "City: " + city_name + "\n" +
                    "Latitude: " + lat + "\n" +
                    "Longitude: " + lon + "\n" +
                    "Temperature (Celsius): " + tempC + "\n" +
                    "Wind Speed (kph): " + windKph + "\n" +
                    "Humidity: " + humidity + "\n" +
                    "Condition: " + conditionText + "\n" +
                    "Date: " + formattedDate;
            return message;
        }  catch (JSONException e) {
            e.printStackTrace();
        }
    return message;
    }
}
