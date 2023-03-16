import java.util.Objects;

public class Order {
    private String Quantity;
    private String WorkPiece;
    private String OrderNumber;
    private String DueDate;
    private String LatePen;
    private String EarlyPen;
    private String Client;

    Order(){}

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public void setWorkPiece(String workPiece) {
        WorkPiece = workPiece;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public void setEarlyPen(String earlyPen) {
        EarlyPen = earlyPen;
    }

    public void setLatePen(String latePen) {
        LatePen = latePen;
    }

    public void printOrder(){
        //System.out.println(OrderNumber + Quantity + WorkPiece);
        System.out.println("----- Printing ORDER -----");
        System.out.println("Number: " + OrderNumber);
        System.out.println("WorkPiece: " + WorkPiece);
        System.out.println("Quantity: " + Quantity);
    }

    public void processOrder(){

        int time, days;
        int numberOfTransformations;
        String tool;

        if(Objects.equals(WorkPiece, "P1")){
            // RAW Material
        }
        else if(Objects.equals(WorkPiece, "P2")){
            // RAW Material
        }
        else if(Objects.equals(WorkPiece, "P3")){
            // P2 - t2/10s -> P3
            time = 10 * Integer.parseInt(Quantity);
            days = time / 60 + 1;
            tool = "T2";

            System.out.println("Raw material needed: P2, quantity: " + Quantity);
            System.out.println("Transformations: P2 to P3 using " + tool);
            System.out.println("Total time in machines: " + time + " seconds. (at least " + days + " days to complete)");


        }
        else if(Objects.equals(WorkPiece, "P4")){
            // P2 - t3/10s -> P4
            time = 10 * Integer.parseInt(Quantity);
            days = time / 60 + 1;
            tool = "T3";

            System.out.println("Raw material needed: P2, quantity: " + Quantity);
            System.out.println("Transformations: P2 to P4 using " + tool);
            System.out.println("Total time in machines: " + time + " seconds. (at least " + days + " days to complete)");
        }
        else if(Objects.equals(WorkPiece, "P5")){
            // P2 - t3/10s -> P4 - t4/10s -> P7 - t3/20s -> P9 - t4/15s -> P5
        }
        else if(Objects.equals(WorkPiece, "P6")){

        }
        else if(Objects.equals(WorkPiece, "P7")){

        }
        else if(Objects.equals(WorkPiece, "P8")){

        }
        else if(Objects.equals(WorkPiece, "P9")){

        }
    }
}
