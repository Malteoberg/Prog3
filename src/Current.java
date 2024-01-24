import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import org.json.JSONObject;


public class Current {
    public static void current() {
        try {

            //Ber användaren om stad och land med hjälp av JOptionPane

            String city = JOptionPane.showInputDialog(null,"Enter city: ", "Weather information", JOptionPane.QUESTION_MESSAGE);

            String API_KEY = "b52bdef2c17941168aa104617240501";
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
                JSONObject jsonResponse = new JSONObject(response.toString());

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
                String message = "Country: " + country_name + "\n" +
                        "City: " + city_name + "\n" +
                        "Latitude: " + lat + "\n" +
                        "Longitude: " + lon + "\n" +
                        "Temperature (Celsius): " + tempC + "\n" +
                        "Wind Speed (kph): " + windKph + "\n" +
                        "Humidity: " + humidity + "\n" +
                        "Condition: " + conditionText + "\n" +
                        "Date: " + formattedDate;
                JOptionPane.showMessageDialog(null,message," Weather information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Error in API request. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
