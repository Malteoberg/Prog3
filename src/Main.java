import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        try {
            //Fråga användaren om land och stad med hjälp av JOptionPane
            String country = JOptionPane.showInputDialog(null, "Enter country: ", "Weather information", JOptionPane.QUESTION_MESSAGE);
            String city = JOptionPane.showInputDialog(null, "Enter city: ", "Weather information", JOptionPane.QUESTION_MESSAGE);

            //Användaren tillfrågas om datum för historiskt väder
            String historicalDate = JOptionPane.showInputDialog(null, "Enter historical date (yyyy-MM-dd): ", "Weather information", JOptionPane.QUESTION_MESSAGE);

            //API-nyckel för att göra förfrågningar till WeatherAPI
            String API_KEY = "b52bdef2c17941168aa104617240501";
            String urlString = "https://api.weatherapi.com/v1/history.json?key=" + API_KEY + "&q=" + city + "&dt=" + historicalDate;

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
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Extrahera data från JSON-objektet
                JSONObject forecast = jsonResponse.getJSONObject("forecast");
                JSONArray forecastday = forecast.getJSONArray("forecastday");

                // Kontrollera om data för det angivna datumet finns tillgänglig
                if (forecastday.length() > 0) {
                    //Antar att det första objektet innehåller data för det angivna datumet
                    JSONObject historicalData = forecastday.getJSONObject(0); // Assuming the first object contains data for the specified date
>
                    //Hämtar relevant information
                    double lat = historicalData.optJSONObject("day").optDouble("avgtemp_c", Double.NaN);
                    double lon = historicalData.optDouble("long", Double.NaN); // Change "long" to the correct key
                    double tempC = historicalData.optJSONObject("day").optDouble("avgtemp_c", Double.NaN);

                    JSONObject dayObject = historicalData.optJSONObject("day");
                    double windKph = dayObject != null ? dayObject.optDouble("maxwind_kph", Double.NaN) : Double.NaN;

                    int humidity = dayObject != null ? dayObject.optInt("avghumidity", -1) : -1;

                    JSONArray conditionArray = dayObject != null ? dayObject.optJSONArray("condition") : null;
                    String conditionText = (conditionArray != null && conditionArray.length() > 0) ?
                            conditionArray.getJSONObject(0).optString("text", "N/A") : "N/A";

                    //Skapar LocalDate objekt från historricalDateObj
                    LocalDate historicalDateObj = LocalDate.parse(historicalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String formattedDate = historicalDateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    //Skapar en ruta där det kommer upp ett meddelande
                    String message = "Country: " + country + "\n" +
                            "City: " + city + "\n" +
                            "Latitude: " + lat + "\n" +
                            "Longitude: " + lon + "\n" +
                            "Temperature (Celsius): " + tempC + "\n" +
                            "Wind Speed (kph): " + windKph + "\n" +
                            "Humidity: " + humidity + "\n" +
                            "Condition: " + conditionText + "\n" +
                            "Date: " + formattedDate;

                    //Visar resultat av det användaren har skrivit in i form av en meddelanderuta
                    JOptionPane.showMessageDialog(null, message, " Weather information", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Visar ett meddelande till användaren om det inte finns data för datumet.
                    JOptionPane.showMessageDialog(null, "No data available for the specified date.", "Weather information", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                //Visar ett felmeddelande om förfrågan på API:n misslyckas
                System.out.println("Error in API request. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
