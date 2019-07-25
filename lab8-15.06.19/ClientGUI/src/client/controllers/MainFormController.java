package client.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.converter.DoubleStringConverter;
import client.ClientSide;
import client.managers.Commander;
import client.managers.FieldsChecker;
import tale.Shorty;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFormController implements FieldsChecker, Initializable {

    @FXML
    private Label statusLabel;

    @FXML
    private TableView<Shorty> shortysTable;

    @FXML
    private TextField nameField;

    @FXML
    private TextField xCoordField;

    @FXML
    private TextField yCoordField;

    @FXML
    private Tab mapTab;

    private Group group = new Group();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusLabel.setText(Commander.getInstance().getEmail() + ", ID - " + Commander.getInstance().getID());
        initTable();
        handleUpdateButtonAction();
    }

    @FXML
    private void initTable() {
        shortysTable.setEditable(true);
        TableColumn<Shorty, String> shortysNameCol = new TableColumn("Name");
        shortysNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        shortysNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        shortysNameCol.setOnEditCommit(event -> {
            if (Commander.getInstance().getID() == event.getTableView().getItems().get(event.getTablePosition().getRow()).getMasterID())
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            else dataEditProhibition();
        });
        TableColumn<Shorty, Double> coordXCol = new TableColumn("X");
        coordXCol.setCellValueFactory(new PropertyValueFactory<>("X"));
        coordXCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        coordXCol.setOnEditCommit(event -> {
            if (Commander.getInstance().getID() == event.getTableView().getItems().get(event.getTablePosition().getRow()).getMasterID())
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setX(event.getNewValue());
            else dataEditProhibition();
        });
        TableColumn<Shorty, Double> coordYCol = new TableColumn<>("Y");
        coordYCol.setCellValueFactory(new PropertyValueFactory<>("y"));
        coordYCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        coordYCol.setOnEditCommit(event -> {
            if (Commander.getInstance().getID() == event.getTableView().getItems().get(event.getTablePosition().getRow()).getMasterID())
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setY(event.getNewValue());
            else dataEditProhibition();
        });
        TableColumn<Shorty, LocalDateTime> birthdayCol = new TableColumn("Birthday");
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        TableColumn masterIDCol = new TableColumn("Master ID");
        masterIDCol.setCellValueFactory(new PropertyValueFactory<>("masterID"));
        shortysTable.getColumns().addAll(shortysNameCol, coordXCol, coordYCol, birthdayCol, masterIDCol);
        fillTable();
    }

    @FXML
    private void fillMap() {
        group.getChildren().clear();
        List<Shorty> shorties = Commander.getInstance().load();
        for (Shorty s: shorties) {
            Circle circle = new Circle(s.getX(), s.getY(), 20, Color.valueOf(colorByID(s.getMasterID())));
            circle.setOnMouseClicked(event -> new AlertHelper(Alert.AlertType.INFORMATION, "INFO", s.toString()).show());
           group.getChildren().add(circle);
        }
        mapTab.setContent(group);
    }

    @FXML
    private void fillTable() {
        shortysTable.setItems(FXCollections.observableArrayList(Commander.getInstance().load()));
        shortysTable.refresh();
    }

    @FXML
    private void handleAddButtonAction() {
        try {
            if (dataIsEntered()) {
                Commander.getInstance().add(nameField.getText(), Double.parseDouble(xCoordField.getText()),
                        Double.parseDouble(yCoordField.getText()));
                handleUpdateButtonAction();
            }
        } catch (NumberFormatException e) {
            notNumberAlert(e);
        }
    }

    @FXML
    private void handleAddIfMinButtonAction() {
        try {
            if (dataIsEntered()) {
                Commander.getInstance().addIfMin(nameField.getText(), Double.parseDouble(xCoordField.getText()),
                        Double.parseDouble(yCoordField.getText()));
                handleUpdateButtonAction();
            }
        } catch (NumberFormatException e) {
            notNumberAlert(e);
        }
    }

    @FXML
    private void notNumberAlert(Exception e) {
        new AlertHelper(Alert.AlertType.ERROR, e.getLocalizedMessage(), ClientSide.languageResource.
                getString("alert.CoordsIllegalMessage")).show();
    }

    @FXML
    private void handleSaveButtonAction() {
        Commander.getInstance().save(new ArrayList<>(shortysTable.getItems()));
        handleUpdateButtonAction();
    }

    @FXML
    private void handleRemoveLastButtonAction() {
        Commander.getInstance().removeLast();
        handleUpdateButtonAction();
    }

    @FXML
    private void handleUpdateButtonAction() {
        fillTable();
        fillMap();
    }

    @FXML
    private void handleDeleteButtonAction() {
        Commander.getInstance().remove(shortysTable.getSelectionModel().getSelectedItem());
        handleUpdateButtonAction();
    }

    @FXML
    private void handleClearButtonAction() {
        Commander.getInstance().clear();
        handleUpdateButtonAction();
    }

    @FXML
    private void handleImportButtonAction() {
        Commander.getInstance().importing();
        handleUpdateButtonAction();
    }

    public void dataEditProhibition() {
        new AlertHelper(Alert.AlertType.ERROR, "ACCESS_DENIED", ClientSide.languageResource.
                getString("alert.dataEditErrorMessage")).show();
        shortysTable.refresh();
    }

    private String colorByID(int ID) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            byte[] hash = mDigest.digest(Integer.toString(ID).getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) hex.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            return hex.toString().substring(0, 6);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "#babbbc";
        }
    }

    @FXML
    @Override
    public boolean dataIsEntered() {
        if (nameField.getText().isEmpty() || xCoordField.getText().isEmpty() || yCoordField.getText().isEmpty()) {
            new AlertHelper(Alert.AlertType.ERROR, "ERROR", ClientSide.languageResource.getString("alert.dataInputErrorMessage")).show();
            return false;
        } else return true;
    }
}