package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import client.ClientSide;
import client.managers.Commander;
import client.managers.FieldsChecker;

public class LoginFormController implements FieldsChecker {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    protected void handleLoginButtonAction() {
        if (dataIsEntered() && Commander.getInstance().login(loginField.getText(), passwordField.getText())) {
            Stage currentWindow = (Stage) loginButton.getScene().getWindow();
            currentWindow.close();
        }
    }

    @FXML
    protected void handleRegisterButtonAction() {
        if (dataIsEntered()) Commander.getInstance().register(loginField.getText(), passwordField.getText());
    }

    @FXML
    protected void handleDeleteButtonAction() {
        if (dataIsEntered()) Commander.getInstance().deleteAccount(loginField.getText(), passwordField.getText());
    }

    @Override
    public boolean dataIsEntered() {
        if (loginField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            new AlertHelper(Alert.AlertType.ERROR, ClientSide.languageResource.getString("alert.emptyField"),
                    ClientSide.languageResource.getString("alert.emptyFieldMessage")).show();
            return false;
        }
        return true;
    }
}