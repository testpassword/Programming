package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.managers.Commander;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientSide extends Application {

    private static FXMLLoader loader = new FXMLLoader();
    public static ResourceBundle languageResource;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        Commander.getInstance();
        loader.setLocation(getClass().getResource("/client/resources/fxml/login_form.fxml"));
        languageResource = ResourceBundle.getBundle("client.resources.Locale", Locale.getDefault());
        loader.setResources(languageResource);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }
}