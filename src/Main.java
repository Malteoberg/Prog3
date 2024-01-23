import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;



public class Main {
    public static void main(String[] args) {
        try {
            // Api key och länk
            String api_key = "b52bdef2c17941168aa104617240501";
            String base_url = "http://api.weatherapi.com/v1/forecast.json";

            // Skapar scanner object for user input
            Scanner scanner = new Scanner(System.in);

            System.out.println("For which city do you want the forecast for?");
            String city = scanner.nextLine();

            System.out.println("How many days ahead do you want the forecast? (1-14)");
            int days = scanner.nextInt();

            // Sätter ihop url och skapar ny URL objekt
            String api_url = base_url + "?key=" + api_key + "&q=" + city + "&days=" + days;

            URL url = new URL(api_url);

            // Öppnar connection till url och sätter metoden till GET
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            // Hämtar respons koden (om 200 är det ok och koden körs vid if)
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK){
                // Läser responsen
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();

                // Parse responsen till json objekt
                JSONObject obj = new JSONObject(response.toString());

                // Hämtar data för att visa
                JSONObject location_data = obj.getJSONObject("location");
                String city_name = location_data.getString("name");
                String country_name = location_data.getString("country");

                JSONObject current_data = obj.getJSONObject("current");
                int temp_c = current_data.getInt("temp_c");

                System.out.println("\nToday, the average temperature in " + city_name + ", " + country_name + " is " + temp_c + "°C\n");

                JSONObject forecast_data = obj.getJSONObject("forecast");
                JSONArray forecastDayArray = forecast_data.getJSONArray("forecastday");

                for (int i = 0; i < forecastDayArray.length(); i++) {
                    JSONObject forecastDayObject = forecastDayArray.getJSONObject(i);

                    // Get the "date" element within forecastday
                    String date = forecastDayObject.getString("date");

                    double avgTempC = forecastDayObject.getJSONObject("day").getDouble("avgtemp_c");
                    int rain = forecastDayObject.getJSONObject("day").getInt("daily_will_it_rain");

                    if (rain == 1){
                        System.out.println("The average temperature on " + date + " will likely be " + avgTempC + "°C and it will most likely rain that day.");
                    } else {
                        System.out.println("The average temperature on " + date + " will likely be " + avgTempC + "°C and it will most likely NOT rain that day.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

