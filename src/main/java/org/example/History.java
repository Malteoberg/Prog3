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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a class for retrieving and formatting historical weather data using a third-party API.
 */
public class History {

    private static final String API_KEY = "b52bdef2c17941168aa104617240501";

    /**
     * Requests user input for the city and the historical date, retrieves the historical weather data,
     * formats it, and displays the information using JOptionPane.
     */
    public void history() {
        //Fråga användaren om land och stad med hjälp av JOptionPane
        String city = JOptionPane.showInputDialog(null, "Enter city: ", "Weather information", JOptionPane.QUESTION_MESSAGE);

        //Användaren tillfrågas om datum för historiskt väder
        String historicalDate = JOptionPane.showInputDialog(null, "Enter historical date (yyyy-MM-dd): (Max 7 days back in time) ", "Weather information", JOptionPane.QUESTION_MESSAGE);

        JSONObject history = getHistory(city, historicalDate);
        String formattedHistory = formatHistory(history, historicalDate);
        if (formattedHistory != null)
            JOptionPane.showMessageDialog(null, formattedHistory, " Weather information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Retrieves historical weather data for a specified city and historical date from the weather API.
     *
     * @param city           The city for which historical weather data is requested.
     * @param historicalDate The historical date (yyyy-MM-dd) for which data is requested.
     * @return A JSONObject containing the historical weather data.
     */
    public static JSONObject getHistory(String city, String historicalDate) {
        JSONObject jsonResponse = null;

        String urlString = "https://api.weatherapi.com/v1/history.json?key=" + API_KEY + "&q=" + city + "&dt=" + historicalDate;

        try {
            //Skapa en URL och öppna anslutning till API
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //Hämta svarskod från API-förfrågan
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //Skapa ett JSON-objekt från API-svaret
                jsonResponse = new JSONObject(response.toString());
            } else {
                //Visar ett felmeddelande om förfrågan på API:n misslyckas
                System.out.println("Error in API request. Response Code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }


    /**
     * Formats historical weather data into a human-readable string.
     *
     * @param history        A JSONObject containing the historical weather data.
     * @param historicalDate The historical date for which data is formatted.
     * @return A formatted string containing historical weather information for the specified date.
     */
    public static String formatHistory(JSONObject history, String historicalDate){
        String message = null;
        if (history == null) return message;
        try {
            // Kontrollera om data för det angivna datumet finns tillgänglig
            // Extrahera data från JSON-objektet
            JSONObject forecast = history.getJSONObject("forecast");
            JSONArray forecastday = forecast.getJSONArray("forecastday");
            JSONObject location = history.getJSONObject("location");
            if (history.length() > 0) {
                //Antar att det första objektet innehåller data för det angivna datumet
                JSONObject historicalData = forecastday.getJSONObject(0); // Assuming the first object contains data for the specified date

                //Hämtar relevant information
                double lat = location.getDouble("lat");
                double lon = location.getDouble("lon");
                String country_name = location.getString("country");
                String city_name = location.getString("name");
                double tempC = historicalData.optJSONObject("day").optDouble("avgtemp_c", Double.NaN);

                JSONObject dayObject = historicalData.optJSONObject("day");
                double windKph = dayObject != null ? dayObject.optDouble("maxwind_kph", Double.NaN) : Double.NaN;
                int humidity = dayObject != null ? dayObject.optInt("avghumidity", -1) : -1;
                String conditionText = dayObject.getJSONObject("condition").getString("text");

                //Skapar LocalDate objekt från historricalDateObj
                LocalDate historicalDateObj = LocalDate.parse(historicalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String formattedDate = historicalDateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                //Skapar en ruta där det kommer upp ett meddelande
                message = "Country: " + country_name + "\n" +
                        "City: " + city_name + "\n" +
                        "Latitude: " + lat + "\n" +
                        "Longitude: " + lon + "\n" +
                        "Temperature (Celsius): " + tempC + "\n" +
                        "Wind Speed (kph): " + windKph + "\n" +
                        "Humidity: " + humidity + "\n" +
                        "Condition: " + conditionText + "\n" +
                        "Date: " + formattedDate;

                //Visar resultat av det användaren har skrivit in i form av en meddelanderuta

            } else {
                    // Visar ett meddelande till användaren om det inte finns data för datumet.
                    JOptionPane.showMessageDialog(null, "No data available for the specified date.", "Weather information", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

}
