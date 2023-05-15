package t.Logic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
    Label ProcessingOrder;
    @FXML
    ListView OrdersList;

    @FXML
    ListView DeliveringPlan;

    @FXML
    ListView PurchasingPlan;

    @FXML
    ListView ProductionPlan;

    @FXML
    ListView Costs;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    seconds++;

                    if(seconds > 59){
                        seconds = 0;
                        day++;
                        MPS.Today = day;

                    }

                    Time.setText("Day: " + day + " (" + seconds + "s)");
                    NumberOfOrders.setText("(" + MPS.numberOfOrders() + ")");

                    List<Order> orders = MPS.getOrders();

                    if(orders!=null){
                        for(Order order : orders){
                            //OrdersList.getItems().add(order.toString());
                            addStringToListView(OrdersList, order.toString());
                        }
                    }

                    /* MPS Processing */



                    List<PurchasingOrder> PurchasingOrders = MPS.getPurchasingOrders();
                    PurchasingPlan.getItems().clear();
                    for (PurchasingOrder purchasingOrder : PurchasingOrders) {
                        // System.out.println(plan.Supplier + " - " + plan.quantity + " - " + plan.purchasing_day);
                        String porder = "From: " + purchasingOrder.Supplier +
                                " Quantity: " + purchasingOrder.quantity +
                                " of Piece: " + purchasingOrder.WorkPiece +
                                " ordered in day: " + purchasingOrder.purchasing_day;
                        PurchasingPlan.getItems().add(porder);
                    }

                    List<DeliveringOrder> DeliveringOrders = MPS.getDeliveringOrders();
                    DeliveringPlan.getItems().clear();
                    for (DeliveringOrder deliveringOrder : DeliveringOrders) {
                        // System.out.println(plan.Supplier + " - " + plan.quantity + " - " + plan.purchasing_day);
                        String dorder = "Day: " + deliveringOrder.Day +
                                " Quantity: " + deliveringOrder.Quantity +
                                " of Piece: " + deliveringOrder.WorkPiece +
                                " from order: " + deliveringOrder.OrderNumber;
                        DeliveringPlan.getItems().add(dorder);
                    }

                    Day[] days = MPS.daysClass;
                    ProductionPlan.getItems().clear();
                    for (int i = 0; i < days.length; i++) {
                        Day currentDay = days[i];
                        // Do something with the currentDay object
                        ProductionPlan.getItems().add("Day: " + i + "-" + currentDay.production());
                    }

                    // List<Order> orders = MPS.getOrders();
                    Costs.getItems().clear();
                    for(Order order : orders){
                        Costs.getItems().add("Order Number: " + order.getOrderNumber() +
                                " Pc = " + order.Pc() +
                                " Dc = " + order.Dc() +
                                " Tc = " + order.Tc());
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
