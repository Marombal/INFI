import java.util.List;

public class DBUpdater extends Thread{

    @Override
    public void run(){

        while(true){
            List<Order> orders = DataBase.uploadUnfinishedOrders();
            if(orders == null){
                System.out.println("No Unfinished Orders to Load from DataBase");
            }else {
                System.out.println("Loading the Unfinished Orders from DataBase");
                for(Order order : orders){
                    MPS.addOrder(order); // order.printOrder(); // mps.printOrders();
                }
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {}
        }
    }
}
