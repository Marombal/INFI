package t.Logic;/*
* Master Production Schedule
*
* Should define for each day in the future
* • the amount of work-pieces of each type you expect to have by the end of that day
* • the capacity used in each warehouse
*
* */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MPS extends Thread{

    static String Number;
    static String PieceInitial;
    static String PieceFinal;
    static String Quantity;
    static float Tc = 0;
    private static List<Order> orders = new ArrayList<>();

    private static List<Order> orders_ordered = new ArrayList<>();

    public static Order ProcessingOrder = null;

    public static int Today = 0;


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

    public static String sendTodayMPS(){
        String PPS = "A" + "0" + "0" + "D" + "0" + "0" + "P" + "0" + "0" + "0";

        return PPS;
    }

    public static float getTc(){
        return Tc;
    }


    public static int DaysToCompleteTransformation;
    public static int NumberOfDeliveringDays;

    public static List<Integer> days = new ArrayList<Integer>();
    public static List<Integer> delivering_days = new ArrayList<Integer>();
    public static void updateMPS(){
        if(orders == null) return;

        // Check if the is any "Processing" Order
        if(ProcessingOrder != null){
            //
        }

        else{
            // Choose the Order with the nearest DueDate
            int LastDueDate = 100000;
            for(Order order : orders){
                if(Integer.parseInt(order.getDueDate()) < LastDueDate){
                    ProcessingOrder = order;
                    LastDueDate = Integer.parseInt(ProcessingOrder.getDueDate());
                }
            }
        }

        DaysToCompleteTransformation = Integer.parseInt(ProcessingOrder.getDueDate()) - Today;

        if (Integer.parseInt(ProcessingOrder.getQuantity()) < 4) {
            NumberOfDeliveringDays = 1;
        } else {
            NumberOfDeliveringDays = ((Integer.parseInt(ProcessingOrder.getQuantity()) - 1) / 4) + 1;
        }

        days.clear();
        for(int i = Today; i < Integer.parseInt(ProcessingOrder.getDueDate()); i++){
            days.add(i);
        }

        delivering_days.clear();
        for(int i = Integer.parseInt(ProcessingOrder.getDueDate()); i > Integer.parseInt(ProcessingOrder.getDueDate()) + 1 - NumberOfDeliveringDays; i--){
            delivering_days.add(i);
        }
    }

    public static void updateMPS2(){
        if(orders == null) return;

        sortOrdersByDueDate();
        setDelivering_days();

        System.out.println("\n\n\n");
        for (Order order : orders_ordered) {
            order.printOrder();
            order.printDeliveries();
        }
    }

    private static void sortOrdersByDueDate() {
        // Use a lambda expression to define the ordering by due date
        Comparator<Order> orderByDueDate = (o1, o2) -> Integer.compare(Integer.parseInt(o1.getDueDate()), Integer.parseInt(o2.getDueDate()));

        // Sort the orders list by due date
        orders.sort(orderByDueDate);

        // Copy the sorted orders to the orders_ordered list
        orders_ordered.clear();
        orders_ordered.addAll(orders);
    }


    public static List<Integer> d_days_list = new ArrayList<Integer>();
    public static int d_days;
    private static void setDelivering_days(){
        for (Order order : orders_ordered) {
            if (Integer.parseInt(order.getQuantity()) < 4) {
                d_days = 1;
            } else {
                d_days = ((Integer.parseInt(order.getQuantity()) - 1) / 4) + 1;
            }

            d_days_list.clear();
            order.removeDeliveries();

            int left_quantity = Integer.parseInt(order.getQuantity());
            int daily_quantity;
            for(int i = Integer.parseInt(order.getDueDate()); i > Integer.parseInt(order.getDueDate()) - d_days; i--){
                d_days_list.add(i);

                daily_quantity = Math.min(left_quantity, 4);

                left_quantity -= daily_quantity;

                Deliver deliver = new Deliver(Integer.toString(i), Integer.toString(daily_quantity));

                order.addDeliver(deliver);
            }


        }
    }
}
