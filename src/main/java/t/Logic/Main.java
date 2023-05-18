package t.Logic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ERP.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 800);
        stage.setTitle("INDUSTRIAL INFORMATICS - ERP");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });

    }

    public static void main(String[] args) {

        /* Process MPS */
        MPS myMPS = new MPS();
        myMPS.SetupMPS();
        //myMPS.start();

        /* Checks DB if there is any unfinished order */
        //DBUpdater.LoadUnfinishedOrders();

        /* Listener UDP to listen to Clients and add orders to DataBase */
        UDPListener myUDPListener = new UDPListener();
        myUDPListener.start();

        /* Listener TCP to listen to MES */
        TCPServer myTCPServer = new TCPServer();
        myTCPServer.start();


        MPS.printOrders();

        /* Launch GUI */
        launch();
    }

    public void exit(Stage stage){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Discordando");
        alert.setHeaderText("EXIT DiscordandoMusic");
        alert.setContentText("Do you want to exit DiscordandoMusic?");

        ButtonType saveAndExitButton = new ButtonType("Save and Exit");
        ButtonType clearAndExitButton = new ButtonType("Clear and Exit");
        ButtonType cancelButton = ButtonType.CANCEL;

        alert.getButtonTypes().setAll(saveAndExitButton, clearAndExitButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == saveAndExitButton) {
                saveState();
                exitApplication();
            } else if (result.get() == clearAndExitButton) {
                clearInformation();
                exitApplication();
            }
        }
    }
    private void saveState() {
        // Implement the logic to save the state of the factory production
    }

    private void clearInformation() {
        // Implement the logic to clear all information
        System.out.println("deleteMPS");
        DataBase.deleteMPS();
        DataBase.insertTime(0,0);
    }

    private void exitApplication() {
        //stage = (Stage) Pane.getScene().getWindow();
        System.out.println("EXIT");
        //stage.close();
        Platform.exit(); // Shutdown the application
        System.exit(0); // Ensure the program terminates
    }

}