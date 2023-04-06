package t.Logic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    LocalTime time = LocalTime.now();

    private int day = 0;
    private int seconds = 0;

    @FXML
    Label Time;
    @FXML
    Label NumberOfOrders;
    @FXML
    Label Tc;

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
                    NumberOfOrders.setText("(" + MPS.numberOfOrders() + ")");
                    Tc.setText("Tc = " + MPS.getTc() + " €");

                    List<Order> orders = MPS.getOrders();

                    if(orders!=null){
                        for(Order order : orders){
                            //OrdersList.getItems().add(order.toString());
                            addStringToListView(OrdersList, order.toString());
                        }
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }


    public static void addStringToListView(ListView<String> listView, String newString) {
        ObservableList<String> items = listView.getItems();
        if (!items.contains(newString)) {
            items.add(newString);
        }
    }
}
