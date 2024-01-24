import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;
import javax.swing.JOptionPane;

public class Forecast {
    public static void forecast() {
        try {
            // Api key och länk
            String api_key = "b52bdef2c17941168aa104617240501";
            String base_url = "http://api.weatherapi.com/v1/forecast.json";

            String city = JOptionPane.showInputDialog(null, "For which city do you want the forecast for? ", "Weather forecast", JOptionPane.QUESTION_MESSAGE);
            String days = JOptionPane.showInputDialog(null, "How many days ahead do you want the forecast? (1-3 including today) ", "Weather forecast", JOptionPane.QUESTION_MESSAGE);

            // Sätter ihop url och skapar ny URL objekt
            String api_url = base_url + "?key=" + api_key + "&q=" + city + "&days=" + days;

            URL url = new URL(api_url);

            // Öppnar connection till url och sätter metoden till GET
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
                JSONObject obj = new JSONObject(response.toString());

                // Hämtar data för att visa
                JSONObject location_data = obj.getJSONObject("location");
                String city_name = location_data.getString("name");
                String country_name = location_data.getString("country");


                JSONObject forecast_data = obj.getJSONObject("forecast");
                JSONArray forecastDayArray = forecast_data.getJSONArray("forecastday");

                // Lägg till en StringBuilder för att bygga upp meddelandet
                StringBuilder messageBuilder = new StringBuilder();

                for (int i = 0; i < forecastDayArray.length(); i++) {
                    JSONObject forecastDayObject = forecastDayArray.getJSONObject(i);

                    // Get the "date" element within forecastday
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

                // Visa det sammanfogade meddelandet i en enda JOptionPane
                JOptionPane.showMessageDialog(null, messageBuilder.toString(), " Weather information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}