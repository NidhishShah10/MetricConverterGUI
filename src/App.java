import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        Label valueLabel = new Label("Value:");
        Label fromUnitLabel = new Label("From Unit:");
        Label toUnitLabel = new Label("To Unit:");

        TextField valueTextField = new TextField();

        ComboBox<String> fromUnitComboBox = new ComboBox<>();
        fromUnitComboBox.getItems().addAll("kg", "gram", "km", "mm", "celsius", "fahrenheit");
        ComboBox<String> toUnitComboBox = new ComboBox<>();
        toUnitComboBox.getItems().addAll("kg", "gram", "km", "mm", "celsius", "fahrenheit");

        Button convertButton = new Button("Convert");
        convertButton.setOnAction(event -> {
            String valueString = valueTextField.getText();
            String fromUnit = fromUnitComboBox.getValue();
            String toUnit = toUnitComboBox.getValue();

            if (valueString.isEmpty() || fromUnit == null || toUnit == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please enter a value and select units.");
                alert.showAndWait();
                return;
            }

            double value;
            try {
                value = Double.parseDouble(valueString);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Value");
                alert.setContentText("Please enter a valid number.");
                alert.showAndWait();
                return;
            }

            double result = convert(value, fromUnit, toUnit);

            if (Double.isNaN(result)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Conversion Not Supported");
                alert.setContentText("Conversion between " + fromUnit + " and " + toUnit + " is not supported.");
                alert.showAndWait();
            } else {
                String message = String.format("%.2f %s = %.2f %s", value, fromUnit, result, toUnit);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Conversion Result");
                alert.setContentText(message);
                alert.showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.add(valueLabel, 0, 0);
        grid.add(valueTextField, 1, 0);
        grid.add(fromUnitLabel, 0, 1);
        grid.add(fromUnitComboBox, 1, 1);
        grid.add(toUnitLabel, 0, 2);
        grid.add(toUnitComboBox, 1, 2);
        grid.add(convertButton, 0, 3);

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Metric Converter");
        primaryStage.show();
    }

    private static double convert(double value, String fromUnit, String toUnit) {
        switch (fromUnit) {
            case "kg":
                return value * 2.20462;
            case "gram":
                return value * 0.03527396;
            case "km":
                return value * 0.621371;
            case "mm":
                return value * 0.0393701;
            case "celsius":
                return (value * 9 / 5) + 32;
            case "fahrenheit":
                return (value - 32) * 5 / 9;
            default:
                return Double.NaN;
        }
    }
}
