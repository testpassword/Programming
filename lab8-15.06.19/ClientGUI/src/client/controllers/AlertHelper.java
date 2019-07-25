package client.controllers;

import javafx.scene.control.Alert;

public class AlertHelper {

    private Alert alert;

    public AlertHelper(Alert.AlertType alertType, String title, String message) {
        alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
    }

    public void show() {
        alert.show();
    }
}