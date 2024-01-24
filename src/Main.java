import javax.swing.JOptionPane;


public class Main {
    public static void main(String[] args) {
        // Create instances of the classes
        Current current_obj = new Current();
        History history_obj = new History();
        Forecast forecast_obj = new Forecast();

        String[] countries = {"Current weather", "Weather Forecast", "Weather history specific date"};
        String selectedCountry = (String) JOptionPane.showInputDialog(null, "Select a country:", "Weather Information",
                JOptionPane.QUESTION_MESSAGE, null, countries, countries[0]);


        if (selectedCountry == "Weather Forecast") {
            // Kalla på metoden forecast från Forecast klassen
            forecast_obj.forecast();
        }
        else if (selectedCountry == "Weather history specific date") {
            history_obj.history();
        }
        else if (selectedCountry == "Current weather"){
            current_obj.current();
        }
    }
}
