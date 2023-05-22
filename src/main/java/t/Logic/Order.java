package t.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private String Quantity;
    private String WorkPiece;
    private String OrderNumber;
    private String DueDate;
    private String LatePen;
    private String EarlyPen;

    private int RealDueDate;
    private int StartDate;
    private String Client = "????";

    private String State;

    private String purchasing_day;

    private String purchasing_quantity;

    private int Ad;

    public List<MRP> mrpList = new ArrayList<>();


    Order(){}

    Order(String client,String number, String WP, String q, String DD, String LP, String EP, String state){
        this.Client = client;
        this.OrderNumber = number;
        this.WorkPiece = WP;
        this.Quantity = q;
        this.DueDate = DD;
        this.LatePen = LP;
        this.EarlyPen = EP;
        this.State = state;
    }

    Order(String client,String number, String WP, String q, String DD, String LP, String EP, String state, String realduedate){
        this.Client = client;
        this.OrderNumber = number;
        this.WorkPiece = WP;
        this.Quantity = q;
        this.DueDate = DD;
        this.LatePen = LP;
        this.EarlyPen = EP;
        this.State = state;
        this.RealDueDate = Integer.parseInt(realduedate);
    }

    Order(String client,String number, String WP, String q, String DD, String LP, String EP, String state, String realduedate, String pd, String pq){
        this.Client = client;
        this.OrderNumber = number;
        this.WorkPiece = WP;
        this.Quantity = q;
        this.DueDate = DD;
        this.LatePen = LP;
        this.EarlyPen = EP;
        this.State = state;
        this.RealDueDate = Integer.parseInt(realduedate);
        this.purchasing_day = pd;
        this.purchasing_quantity = pq;
    }

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

    public void setState(String state){
        State = state;
    }

    public void setRealDueDate(int realDueDate) {
        RealDueDate = realDueDate;
    }

    public void setStartDate(int startDate) {
        StartDate = startDate;
    }

    public void setClient(String client) {
        Client = client;
    }

    public void setAd(int ad) {
        Ad = ad;
    }

    public void setPurchasing_day(String purchasing_day) {
        this.purchasing_day = purchasing_day;
    }

    public void setPurchasing_quantity(String purchasing_quantity) {
        this.purchasing_quantity = purchasing_quantity;
    }

    public String getDueDate() {
        return DueDate;
    }

    public String getOrderNumber(){
        return OrderNumber;
    }
    public String getQuantity() {
        return Quantity;
    }

    public String getWorkPiece() {
        return WorkPiece;
    }

    public int getRealDueDate() {
        return RealDueDate;
    }

    public int getStartDate() {
        return StartDate;
    }

    public String getClient() {
        return Client;
    }

    public String getPurchasing_day() {
        return purchasing_day;
    }

    public String getPurchasing_quantity() {
        return purchasing_quantity;
    }

    public void printOrder(){
        //System.out.println(OrderNumber + Quantity + WorkPiece);
        System.out.println("----- Printing ORDER -----");
        System.out.println("Number: " + this.OrderNumber);
        System.out.println("WorkPiece: " + this.WorkPiece);
        System.out.println("Quantity: " + this.Quantity);
        System.out.println("DueDate: " + this.DueDate);
        System.out.println("LatePen: " + this.LatePen);
        System.out.println("EarlyPen: " + this.EarlyPen);
        System.out.println("State: " + this.State);
    }


    public String toString(){

        return "Client: " + this.Client
                + " Number: " + this.OrderNumber
                + " WorkPiece: " + this.WorkPiece
                + " Quantity: " + this.Quantity
                + " DueDate: " + this.DueDate
                + " LatePen: " + this.LatePen
                + " EarlyPen: " + this.EarlyPen;
                //+ " State: " + this.State;
    }

    public void InsertOrderInDB(){
        DataBase.insertOrder(this.Client
                , this.OrderNumber
                , this.WorkPiece
                , this.Quantity
                , this.DueDate
                , this.EarlyPen
                , this.LatePen
                ,"WAITING");
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
            time = Integer.parseInt(Quantity) * (10 + 10 + 20 + 15);
            days = time / 60 + 1;
            tool = "T3 + T4 + T3 + T4";

            System.out.println("Raw material needed: P2, quantity: " + Quantity);
            System.out.println("Transformations: P2 to P4 to P7 to P9 to P5 using " + tool);
            System.out.println("Total time in machines: " + time + " seconds. (at least " + days + " days to complete)");
        }
        else if(Objects.equals(WorkPiece, "P6")){
            // P1 - t1/20s -> P6
            time = Integer.parseInt(Quantity) * (20);
            days = time / 60 + 1;
            tool = "T1";

            System.out.println("Raw material needed: P1, quantity: " + Quantity);
            System.out.println("Transformations: P1 to P6 using " + tool);
            System.out.println("Total time in machines: " + time + " seconds. (at least " + days + " days to complete)");

        }
        else if(Objects.equals(WorkPiece, "P7")){
            // P2 - t3/10s -> P4 - t4/10s -> P7
            time = Integer.parseInt(Quantity) * (10 + 10);
            days = time / 60 + 1;
            tool = "T3 + T4";

            System.out.println("Raw material needed: P2, quantity: " + Quantity);
            System.out.println("Transformations: P2 to P4 to P7 using " + tool);
            System.out.println("Total time in machines: " + time + " seconds. (at least " + days + " days to complete)");
        }
        else if(Objects.equals(WorkPiece, "P8")){
            // P1 - t1/20s -> P6 - t3/30s -> P8
            time = Integer.parseInt(Quantity) * (20 + 30);
            days = time / 60 + 1;
            tool = "T1 + T3";

            System.out.println("Raw material needed: P1, quantity: " + Quantity);
            System.out.println("Transformations: P1 to P6 to P8 using " + tool);
            System.out.println("Total time in machines: " + time + " seconds. (at least " + days + " days to complete)");
        }
        else if(Objects.equals(WorkPiece, "P9")){
            // P2 - t3/10s -> P4 - t4/10s -> P7 - t3/20s -> P9
            time = Integer.parseInt(Quantity) * (10 + 10 + 20);
            days = time / 60 + 1;
            tool = "T3 + T4 + T3";

            System.out.println("Raw material needed: P2, quantity: " + Quantity);
            System.out.println("Transformations: P2 to P4 to P7 to P9 using " + tool);
            System.out.println("Total time in machines: " + time + " seconds. (at least " + days + " days to complete)");
        }
    }


    public String RawMaterial(){
        if(Objects.equals(WorkPiece, "P3")) return "P2";
        else if(Objects.equals(WorkPiece, "P4")) return "P2";
        else if(Objects.equals(WorkPiece, "P5")) return "P2";
        else if(Objects.equals(WorkPiece, "P6")) return "P1";
        else if(Objects.equals(WorkPiece, "P7")) return "P2";
        else if(Objects.equals(WorkPiece, "P8")) return "P1";
        else if(Objects.equals(WorkPiece, "P9")) return "P2";

        return null;
    }

    public int estimateTime(){
        return 20;
    }

    public int numberOfTransformations(){
        if(Objects.equals(WorkPiece, "P3")) return 1;
        else if(Objects.equals(WorkPiece, "P4")) return 1;
        else if(Objects.equals(WorkPiece, "P5")) return 4;
        else if(Objects.equals(WorkPiece, "P6")) return 1;
        else if(Objects.equals(WorkPiece, "P7")) return 2;
        else if(Objects.equals(WorkPiece, "P8")) return 2;
        else if(Objects.equals(WorkPiece, "P9")) return 3;

        return -1;
    }

    public float Rc(){
        float Rc;
        if(Objects.equals(RawMaterial(), "P1")){
            Rc = Integer.parseInt(Quantity) * 55;
        }
        else{
            Rc = Integer.parseInt(Quantity) * 18;
        }

        return Rc;
    }

    public float Dc(){
        float Dc;

        Dc = (float) (Rc() * (RealDueDate - StartDate) * 0.01);

        return Dc;
    }

    public float Pc(){
        return 0;
    }

    public float Tc(){
        return Rc() + Dc() + Pc();
    }

    public float TotalCost(){
        return Tc() * Integer.parseInt(Quantity) - (RealDueDate - StartDate) * Integer.parseInt(LatePen);
    }

}
