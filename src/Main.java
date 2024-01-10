

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.HTTP;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String API_KEY = "b52bdef2c17941168aa104617240501";
    public static void main(String[] args) throws JSONException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        JSONObject countryWeather = getCountryJson("sweden");
    }

    public static JSONObject getCountryJson(String country) throws JSONException {

        JSONObject data = new JSONObject();
        try {
            // Code
            // www.weatherapi.com
            String k = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + country;

            URL url = new URL(k);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK){
                java.io.BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();

                data = new JSONObject(response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            return getWeatherInfo(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static JSONObject getWeatherInfo(JSONObject data) throws JSONException {
        JSONObject weatherInfo = new JSONObject();
        JSONObject location = data.getJSONObject("location");
        JSONObject current = data.getJSONObject("current");
        weatherInfo.put("name", location.getString("name"));
        weatherInfo.put("region", location.getString("region"));
        weatherInfo.put("country", location.getString("country"));
        weatherInfo.put("localtime", location.getString("localtime"));
        weatherInfo.put("temp_c", current.getDouble("temp_c"));
        weatherInfo.put("temp_f", current.getDouble("temp_f"));
        weatherInfo.put("description", current.getJSONObject("condition").getString("text"));
        System.out.println(weatherInfo);

        return weatherInfo;
    }
}

/*"{\n" +
                    "    \"location\": {\n" +
                    "        \"name\": \"Stockholm\",\n" +
                    "        \"region\": \"Stockholms Lan\",\n" +
                    "        \"country\": \"Sweden\",\n" +
                    "        \"lat\": 59.33,\n" +
                    "        \"lon\": 18.05,\n"
            "        \"tz_id\": \"Europe/Stockholm\",\n" +
                    "        \"localtime_epoch\": 1704451663,\n" +
                    "        \"localtime\": \"2024-01-05 11:47\"\n" +
                    "    },\n" +
                    "    \"current\": {\n" +
                    "        \"last_updated_epoch\": 1704451500,\n" +
                    "        \"last_updated\": \"2024-01-05 11:45\",\n" +
                    "        \"temp_c\": -10,\n" +
                    "        \"temp_f\": 14,\n" +
                    "        \"is_day\": 1,\n" +
                    "        \"condition\": {\n" +
                    "            \"text\": \"Partly cloudy\",\n" +
                    "            \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/116.png\",\n" +
                    "            \"code\": 1003\n" +
                    "        },\n" +
                    "        \"wind_mph\": 5.6,\n" +
                    "        \"wind_kph\": 9,\n" +
                    "        \"wind_degree\": 350,\n" +
                    "        \"wind_dir\": \"N\",\n" +
                    "        \"pressure_mb\": 1011,\n" +
                    "        \"pressure_in\": 29.85,\n" +
                    "        \"precip_mm\": 0,\n" +
                    "        \"precip_in\": 0,\n" +
                    "        \"humidity\": 92,\n" +
                    "        \"cloud\": 25,\n" +
                    "        \"feelslike_c\": -16.1,\n" +
                    "        \"feelslike_f\": 3,\n" +
                    "        \"vis_km\": 10,\n" +
                    "        \"vis_miles\": 6,\n" +
                    "        \"uv\": 1,\n" +
                    "        \"gust_mph\": 11.9,\n" +
                    "        \"gust_kph\": 19.1\n" +
                    "    }\n" +
<<<<<<< Updated upstream
                    "}"*/

            URL url = new URL(k);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK){
                java.io.BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
                JSONObject jsonResponse = new JSONObject(response.toString());
                String target = "location";
                JSONObject data = jsonResponse.getJSONObject(target);

                String country = data.getString("country");
                Double lat = data.getDouble("lat");

                System.out.println(jsonResponse);
                System.out.println(data);
                System.out.println(country);
                System.out.println(lat);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    public JSONObject getCountry(String country){

        }
    }
} // test
=======
                    "}"*/
>>>>>>> Stashed changes
