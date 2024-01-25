import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        WeatherApp weatherApp = new WeatherApp();
        weatherApp.start();
    }
}

class WeatherApp {
    // Instansierar klasserna
    private Current current;
    private History history;
    private Forecast forecast;

    // Konstruktörer för insiitiera instanserna av klasserna
    public WeatherApp() {
        this.current = new Current();
        this.history = new History();
        this.forecast = new Forecast();
    }

    public void start() {
        String[] options = {"Current weather", "Weather Forecast", "Weather history specific date"};
        String selectedOption = (String) JOptionPane.showInputDialog(
                null, "Select your choice: ", "Weather Information",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]
        );

        switch (selectedOption) {
            case "Weather Forecast":
                forecast.forecast();
                break;
            case "Weather history specific date":
                history.history();
                break;
            case "Current weather":
                current.current();
                break;
            default:
                // Handle invalid selection
                break;
        }
    }
}