/*
* Master Production Schedule
*
* Should define for each day in the future
* • the amount of work-pieces of each type you expect to have by the end of that day
* • the capacity used in each warehouse
*
* */

import java.util.ArrayList;
import java.util.List;
public class MPS {

    String Number, PieceInitial, PieceFinal, Quantity;

    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order e){
        if(e!=null)
            orders.add(e);
    }
    public List<Order> getOrders() {
        return orders;
    }

    public int numberOfOrders(){
        return orders.size();
    }

    public void printOrders(){
        for(Order order : orders){
            order.printOrder();
        }
    }

    public void sendTodayMPS(){
        String msg = "!" + Number + PieceInitial + PieceFinal + Quantity;
    }
}
