package t.Logic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


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
    }

    public static void main(String[] args) {

        /* Process MPS */
        MPS myMPS = new MPS();
        //myMPS.start();

        /* Checks DB if there is any unfinished order */
        DBUpdater.LoadUnfinishedOrders();

        /* Listener UDP to listen to Clients and add orders to DataBase */
        UDPListener myUDPListener = new UDPListener();
        myUDPListener.start();

        MPS.printOrders();

        /* Launch GUI */
        launch();
    }
}