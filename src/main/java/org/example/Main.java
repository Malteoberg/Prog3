package org.example;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
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
        while (true) {
            // Rullgardin för användaren att välja option
            String[] options = {"Current weather", "Weather Forecast", "Weather history specific date"};
            String selectedOption = (String) JOptionPane.showInputDialog(
                    null, "Select your choice: ", "Weather Information",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]
            );
            if (selectedOption == null) return;

            // Switch för att hantera vad som körs beroende på användarens val
            switch (selectedOption) {
                case "Weather Forecast":
                    forecast.forecast();
                case "Weather history specific date":
                    history.history();
                case "Current weather":
                    current.current();
                default:
                    // Handle invalid selection
                    break;
            }
        }
    }
}