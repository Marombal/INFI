package t.Logic;/*
* Master Production Schedule
*
* Should define for each day in the future
* • the amount of work-pieces of each type you expect to have by the end of that day
* • the capacity used in each warehouse
*
* */

import java.util.ArrayList;
import java.util.List;

public class MPS extends Thread{

    static String Number;
    static String PieceInitial;
    static String PieceFinal;
    static String Quantity;
    private static List<Order> orders = new ArrayList<>();

    @Override
    public void run(){
        while(true){
            System.out.println("Updating MPS...");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {}
        }
    }



    public static void addOrder(Order e){
        if(e!=null)
            orders.add(e);
    }
    public static List<Order> getOrders() {
        return orders;
    }

    public static int numberOfOrders(){
        return orders.size();
    }

    public static void printOrders(){
        for(Order order : orders){
            order.printOrder();
        }
    }

    public static void sendTodayMPS(){
        String msg = "!" + Number + PieceInitial + PieceFinal + Quantity;
    }
}
