import javax.swing.JOptionPane;


public class Main {
    public static void main(String[] args) {
        // Skapa instanser av klasserna
        Current current_obj = new Current();
        History history_obj = new History();
        Forecast forecast_obj = new Forecast();

        String[] options = {"Current weather", "Weather Forecast", "Weather history specific date"};
        String selectedCountry = (String) JOptionPane.showInputDialog(null, "Select a option:", "Weather Information",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);


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
