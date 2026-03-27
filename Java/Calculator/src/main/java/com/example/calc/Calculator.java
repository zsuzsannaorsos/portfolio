package com.example.calc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * A simple JavaFX Calculator application.
 * Supports basic math and keyboard shortcuts for clearing and calculating.
 * Clear the display with ESC or DELETE button.
 * Shows result after ENTER is pressed too
 */


public class Calculator extends Application {

    private double firstNumber = 0;
    private String operator = "";
    private TextField display;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 400);

        //keyboard event handling
        scene.setOnKeyPressed(event -> {
            //clear display on ESC or DELETE
            if (event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.ESCAPE) {
                firstNumber = 0;
                operator = "";
                display.setText("");
            }
            //calculate on ENTER
            if (event.getCode() == KeyCode.ENTER) {
                calculate();
            }
        });
        stage.setTitle("Calculator");
        stage.setScene(scene);

        //display window on top for the typed in numbers
        display = new TextField();
        display.setEditable(false); //prevents manual typing
        display.setStyle("-fx-font-size: 30px;");
        display.setAlignment(Pos.CENTER_RIGHT);
        root.setTop(display);

        //grid for the numbers
        GridPane grid = new GridPane();
        root.setCenter(grid);
        grid.add(new Button("7"), 0, 0);

        String[] buttons = {"7", "8", "9", "x",
                "4", "5", "6", "+",
                "1", "2", "3", "-",
                ".", "0", "/", "="};

        int col = 0;
        int row = 0;
        for (String button : buttons) {
            Button b = new Button(button);
            b.setFocusTraversable(false);
            //make the button fill the grid cell
            b.prefWidthProperty().bind(grid.widthProperty().divide(4));
            b.prefHeightProperty().bind(grid.heightProperty().divide(4));

            //make the text bigger
            b.setStyle("-fx-font-size: 20px;");

            //adding the number values, the operators and equal
            b.setOnAction(e -> {
                String value = b.getText();
                if (!"x-+/=".contains(value)) {
                    display.setText(display.getText() + value);
                }
                //
                else if (!"=".contains(value)) {
                    firstNumber = Double.parseDouble(display.getText());
                    operator = value;
                    display.setText("");
                }
                if (value.equals("=")) {
                    calculate();
                }

            });
            grid.add(b, col, row);
            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }

        stage.show();
        scene.setOnMouseClicked(e -> scene.getRoot().requestFocus());
        scene.getRoot().requestFocus();


    }

    /**
     * Performs the arithmetic calculation based on the stored operator
     * and the current value in the display.
     */

    private void calculate() {
        double secondNumber = Double.parseDouble(display.getText());

        double result = 0;
        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;

            case "-":
                result = firstNumber - secondNumber;
                break;

            case "x":
                result = firstNumber * secondNumber;
                break;

            case "/":
                if (secondNumber == 0) {
                    display.setText("Cannot divide by 0"); //handle division by zero
                    return;
                }
                result = firstNumber / secondNumber;
                break;
        }
        display.setText(Double.toString(result));
    }
}