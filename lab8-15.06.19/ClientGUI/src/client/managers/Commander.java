package client.managers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.stage.FileChooser;
import client.ClientSide;
import client.controllers.AlertHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tale.Shorty;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;

public class Commander {

    private static Commander instance;
    private String email;
    private String password;
    private int ID;
    private Gson parser = new Gson();
    private List<Shorty> incomingList;
    private Stage mainWindow;

    public static Commander getInstance() {
        if (instance == null) {
            instance = new Commander();
        }
        return instance;
    }

    public boolean login(String email, String password) {
        this.ID = Integer.parseInt(requestToServer("login " + email + " " + password));
        try {
            if (this.ID != 0) {
                this.email = email;
                this.password = password;
                this.mainWindow = new Stage();
                this.mainWindow.setTitle("Main window");
                this.mainWindow.setResizable(false);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/client/resources/fxml/main_form.fxml"));
                loader.setResources(ResourceBundle.getBundle("client.resources.Locale", Locale.getDefault()));
                this.mainWindow.setScene(new Scene(loader.load()));
                this.mainWindow.show();
                return true;
            } else new AlertHelper(Alert.AlertType.ERROR, "ERROR", ClientSide.languageResource.getString("alert.loginErrorMessage")).show();
            return false;
        } catch (IOException e) {
            e.printStackTrace(); //Никогда эта херня не выскочит, так как ресурсы FXML внутри jar-ника, но JAVAAAAAA!!!
            return false;
        }
    }

    public void importing() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open json file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File localCollectionFile = fileChooser.showOpenDialog(mainWindow);
        try (BufferedReader inputStreamReader = new BufferedReader(new FileReader(localCollectionFile))) {
            String nextLine;
            StringBuilder result = new StringBuilder();
            while ((nextLine = inputStreamReader.readLine()) != null) result.append(nextLine);
            new AlertHelper(Alert.AlertType.INFORMATION, "ANSWER", requestToServer(email + " " + password + " " + "import " + result.toString())).show();
        } catch (IOException e) {
            new AlertHelper(Alert.AlertType.INFORMATION, "ANSWER", e.getLocalizedMessage()).show();
        }
    }

    public void removeLast() {
        new AlertHelper(Alert.AlertType.INFORMATION, "ANSWER", requestToServer(email + " " + password + " " + "remove_last")).show();
    }

    public void clear() {
        new AlertHelper(Alert.AlertType.INFORMATION, "ANSWER", requestToServer(email + " " + password + " " + "clear")).show();
    }

    public void add(String name, double x, double y) {
        Shorty s = new Shorty(name, x, y, LocalDateTime.now(), ID);
        new AlertHelper(Alert.AlertType.INFORMATION, "ANSWER", requestToServer(email + " " + password + " " + "add " + parser.toJson(s))).show();
    }

    public void addIfMin(String name, double x, double y) {
        Shorty s = new Shorty(name, x, y, LocalDateTime.now(), ID);
        new AlertHelper(Alert.AlertType.INFORMATION, "ANSWER", requestToServer(email + " " + password + " " + "add_if_min " + parser.toJson(s))).show();
    }

    public void register(String login, String password) {
        new AlertHelper(Alert.AlertType.INFORMATION, "ANSWER", requestToServer("register " + login + " " + password)).show();
    }


    public void deleteAccount(String login, String password) {
        new AlertHelper(Alert.AlertType.INFORMATION, "ANSWER", requestToServer("delete_account " + login + " " + password)).show();
    }

    public List<Shorty> load() {
        String answer = requestToServer(email + " " + password + " show");
        incomingList = parser.fromJson(answer, new TypeToken<ArrayList<Shorty>>(){}.getType());

        return incomingList;
    }

    public void remove(Shorty s) {
        new AlertHelper(Alert.AlertType.INFORMATION, "ANSWER", requestToServer(email + " " + password + " " + "remove " + parser.toJson(s))).show();
    }

    public void save(List<Shorty> list) {
        requestToServer(email + " " + password + " " + "update " + parser.toJson(list));
        new AlertHelper(Alert.AlertType.INFORMATION, "ANSWER", requestToServer(email + " " + password + " " + "save")).show();
    }

    private String requestToServer(String request) {
        try (Socket outcoming = new Socket(InetAddress.getLocalHost(), 8800)) {
            outcoming.setSoTimeout(5000);
            try (ObjectOutputStream toServer = new ObjectOutputStream(outcoming.getOutputStream());
                 ObjectInputStream fromServer = new ObjectInputStream(outcoming.getInputStream())) {
                toServer.writeObject(request);
                return fromServer.readObject().toString();
            }
        } catch (IOException | ClassNotFoundException e) {
            return e.getLocalizedMessage();
        }
    }

    public int getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }
}