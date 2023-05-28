package t.Logic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    ListView MRP;

    @FXML
    ListView Costs;

    @FXML
    Button SaveExit;

    @FXML
    Button ClearExit;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        day = DataBase.getDay();
        seconds = DataBase.getSec();
        MPS.Today = day;


        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    seconds++;//MPS.sendTodayMPS();

                    if(seconds > 59){
                        seconds = 0;
                        day++;
                        MPS.Today = day;

                        MPS.sendTodayMPS();
                    }
                    MPS.Seconds = seconds;
                    if (seconds % 10 == 0) {
                        // The value of 'seconds' is a multiple of 10
                        DataBase.insertTime(day, seconds);
                        System.out.println("Update DB timers (nº of queries " + DataBase.numberOfDataBaseQuerys + ")");

                        //MPS.sendTodayMPS();
                        // DataBase.loadOrders();
                    }

                    Time.setText("Day: " + day + " (" + seconds + "s)");
                    NumberOfOrders.setText("(" + MPS.numberOfOrders() + ")");

                    List<Order> orders = MPS.getOrders();

                    if(orders!=null){
                        for(Order order : orders){
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

                    //List<Order> orders = MPS.getOrders();
                    MRP.getItems().clear();
                    if(orders!=null){
                        for(Order order : orders){
                            List<MRP> Mrp = order.mrpList;
                            for(MRP mrp : Mrp){
                                MRP.getItems().add("Day: " + mrp.getDay() +
                                        " from order: " + mrp.getOrderNumber() +
                                        " Quantity: " + mrp.getQuantity() +
                                        " WorkPiece: " + mrp.getMaterialType());
                            }
                        }
                    }

                    // List<Order> orders = MPS.getOrders();
                    Costs.getItems().clear();
                    for(Order order : orders){
                        if(order.getRealDueDate() <= day){
                            Costs.getItems().add("Order Number: " + order.getOrderNumber() +
                                    " Average Piece Cost: " + order.Tc() +
                                    " Total Order Cost = " + order.TotalCost());
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


    private Stage stage;
    private Parent root;
    private Scene scene;
    @FXML
    AnchorPane Pane;


    public void exit(ActionEvent e) {
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
        DataBase.deleteOrders();
        DataBase.deleteMPS();
        DataBase.deleteMRP();
        DataBase.insertTime(0,0);
    }

    private void exitApplication() {
        stage = (Stage) Pane.getScene().getWindow();
        System.out.println("EXIT");
        stage.close();
        Platform.exit(); // Shutdown the application
        System.exit(0); // Ensure the program terminates
    }



}
