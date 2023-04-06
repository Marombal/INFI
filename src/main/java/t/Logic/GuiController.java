package t.Logic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    LocalTime time = LocalTime.now();

    private int day = 0;
    private int seconds = 0;

    @FXML
    Label Time;

    @FXML
    ListView OrdersList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    seconds++;

                    if(seconds > 59){
                        seconds = 0;
                        day++;
                    }

                    Time.setText("Day: " + day + " (" + seconds + "s)");
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        OrdersList.getItems().add("ola");
    }
}
