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
import java.util.Objects;

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
    public static int Seconds = 0;

    public static Day[] daysClass = new Day[100];
    public static Stock[] stockClass = new Stock[100];


    @Override
    public void run(){
        while(true){
            System.out.println("Updating MPS...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {}
        }
    }


    public void SetupMPS(){

        int[][] data = DataBase.selectAllDays();
        orders = DataBase.loadAllOrders();
        deliveringOrders = calculateDOrders();
        purchasingOrders = calculatePOrders();

        List<MRP> mrptem = DataBase.selectMRP();
        for (Order order : orders) {
            String orderNumber = order.getOrderNumber();
            List<MRP> associatedMRPs = new ArrayList<>();

            for (MRP mrp : mrptem) {
                if (mrp.getOrderNumber().equals(orderNumber)) {
                    associatedMRPs.add(mrp);
                }
            }

            order.mrpList = associatedMRPs;
        }


        for (int i = 0; i < 100; i++) {
            daysClass[i] = new Day(i);
            stockClass[i] = new Stock(i);

            //int[] data = DataBase.selectDay(i);
            if(data != null){
                daysClass[i].setComingP1(data[i][1]);
                daysClass[i].setComingP2(data[i][2]);
                daysClass[i].setProductionP3(data[i][3]);
                daysClass[i].setProductionP4(data[i][4]);
                daysClass[i].setProductionP5(data[i][5]);
                daysClass[i].setProductionP6(data[i][6]);
                daysClass[i].setProductionP7(data[i][7]);
                daysClass[i].setProductionP8(data[i][8]);
                daysClass[i].setProductionP9(data[i][9]);
                daysClass[i].setDeliverP3(data[i][10]);
                daysClass[i].setDeliverP4(data[i][11]);
                daysClass[i].setDeliverP5(data[i][12]);
                daysClass[i].setDeliverP6(data[i][13]);
                daysClass[i].setDeliverP7(data[i][14]);
                daysClass[i].setDeliverP8(data[i][15]);
                daysClass[i].setDeliverP9(data[i][16]);
            }

        }
    }

    public static Day[] cloneDaysClass() throws CloneNotSupportedException {
        Day[] clonedDaysClass = new Day[daysClass.length];
        for (int i = 0; i < daysClass.length; i++) {
            clonedDaysClass[i] = (Day) daysClass[i].clone();
        }
        return clonedDaysClass;
    }

    private static List<DeliveringOrder> calculateDOrders() {
        List<DeliveringOrder> deliveringOrders = new ArrayList<>();

        for (Order order : orders) {
            DeliveringOrder deliveringOrder = new DeliveringOrder(order.getRealDueDate(),
                    order.getWorkPiece(),
                    Integer.parseInt(order.getQuantity()),
                    order.getOrderNumber(),
                    order.getClient());

            deliveringOrders.add(deliveringOrder);
        }

        return deliveringOrders;
    }

    private static List<PurchasingOrder> calculatePOrders() {
        List<PurchasingOrder> pOrders = new ArrayList<>();

        for (Order order : orders) {
            //System.out.println(order.getRealDueDate());
            if(order.getPurchasing_quantity() != null && order.getPurchasing_day() != null){
                if(Integer.parseInt(order.getPurchasing_quantity()) > 0 && Integer.parseInt(order.getPurchasing_day())>0){
                    PurchasingOrder pO = new PurchasingOrder("Supplier C",
                            Integer.parseInt(order.getPurchasing_quantity()),
                            Integer.parseInt(order.getPurchasing_day()),
                            order.getWorkPiece());

                    pOrders.add(pO);
                }
            }
        }

        return pOrders;
    }



    public static void print20days(){
        for (int i = 0; i < 20; i++) {
            daysClass[i].printDay();
            stockClass[i].printStock();
        }
    }

    public static void print20daysMES(){
        for (int i = 0; i < 20; i++) {
            daysClass[i].sendDay();
        }
    }

    public static void print10days(){
        for (int i = 0; i < 11; i++) {
            daysClass[i].printDay();
            stockClass[i].printStock();
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
        System.out.println("Sending Daily Planing");
        TCPSender.sendString(daysClass[Today].sendDay());
    }

    public static float getTc(){
        return Tc;
    }

    public static List<PurchasingOrder> getPurchasingOrders() {
        return purchasingOrders;
    }

    public static List<DeliveringOrder> getDeliveringOrders() {
        return deliveringOrders;
    }


    static List<PurchasingOrder> purchasingOrders = new ArrayList<>();

    static List<DeliveringOrder> deliveringOrders = new ArrayList<>();

    static List<ProductionOrder> productionOrders = new ArrayList<>();

    public static void updateMPS3 (Order order){
        // This function will process one and only one order
        Order OrderTest = order;
        int today_day = MPS.Today;
        int starting_day = today_day + 1;
        int quantity = Integer.parseInt(OrderTest.getQuantity());

        // Purchasing Plan
        // checks how many pieces are already available at stock

        // MAROMBAL lets consider no stock available
        // If purchasing is needed. Determine the type of Raw Material and Quantity
        String raw = OrderTest.RawMaterial();
        int Quantity_Of_Purchasing;
        if(quantity < 4) Quantity_Of_Purchasing = 1;
        else Quantity_Of_Purchasing = (quantity - 1) / 4 + 1;

        // Set day when the items will arrive
        if(Objects.equals(raw, "P1")) daysClass[starting_day].setComingP1(quantity);
        if(Objects.equals(raw, "P2")) daysClass[starting_day].setComingP2(quantity);

        // Production Plan
        /*
        * 1. Gets the production starting day
        * 2. Gets the estimating time to make 1 transformation
        * 3. Calculates the total number of days to complete all transformations
        * 4. Allocates space in dayClass
        * */
        int production_starting_day = starting_day + 1;
        int estimate_time = OrderTest.estimateTime();
        int num_of_days_to_production = calculateNumberOfPeriods(estimate_time, quantity);
        //allocate_days_to_production(production_starting_day, quantity, num_of_days_to_production, tasksIn60Seconds(e),"P6");



        // Delivering Plan
        /*
        * 1. Gets the delivering starting day (already done in allocate_days_to_production)
        * 2. Gets how many days will be needed to deliver
        * 3. Allocates delivering days
        * */
        int delivering_start_day = 0;
        int num_of_days_to_deliver;
        if(quantity < 4) num_of_days_to_deliver = 1;
        else num_of_days_to_deliver = (quantity - 1) / 4 + 1;
        allocate_days_to_deliver(delivering_start_day, quantity, "");

    }

    public static void updateMPS4 (Order order) throws CloneNotSupportedException {

        int delivering_day = checkTime(order);

        order.setRealDueDate(delivering_day);
        //System.out.println("\n\nBSJADSABKJADBKJASDBJKADBJK     " + delivering_day);

        // This function will process one and only one order
        Order OrderTest = order;
        int today_day = MPS.Today;
        int starting_day = today_day + 1;
        int quantity = Integer.parseInt(OrderTest.getQuantity());

        // Delivering Plan
        /*
         * 1. Gets the delivering day
         * 2. Gets delivering quantity
         * 3. Gets delivering piece
         * 4. Allocates delivering day
        * */
        //int delivering_day = Integer.parseInt(OrderTest.getDueDate());          // 1.
        int delivering_quantity = quantity;                                     // 2.
        String delivering_piece = OrderTest.getWorkPiece();                     // 3.

        while(daysClass[delivering_day].totalDelivering() + delivering_quantity > 8){
            delivering_day++;
        }

        daysClass[delivering_day].setDeliverQuantity(delivering_quantity);      // 4.
        daysClass[delivering_day].setDeliverPiece(delivering_piece);            // 4.

        daysClass[delivering_day].addDelivering(delivering_quantity, delivering_piece);

        DeliveringOrder deliveringOrder1 = new DeliveringOrder(delivering_day, delivering_piece, quantity, OrderTest.getOrderNumber(), OrderTest.getClient());
        deliveringOrders.add(deliveringOrder1);


        // Production Plan
        /*
        * //Gets the number of simple transformations
        * Gets the last day when all production need to be completed
        * Gets quantity
        * Gets estimate production time
        * Gets the number of days to produce
        * Allocate days based on quantity to produce
        * */
        ProductionOrder productionOrder1 = new ProductionOrder(OrderTest.getClient(), OrderTest.getOrderNumber());

        int production_last_day = delivering_day - 1;
        int producing_quantity = quantity;
        String production_piece = OrderTest.getWorkPiece();
        int production_time = estimatePieceTime(production_piece);
        int producing_days = calculateNumberOfPeriods(production_time, producing_quantity);

        int max = tasksIn60Seconds(production_time);
        //if(max > 2) max = 2;



        MRP mrp2 = new MRP(production_last_day + 1, producing_quantity, production_piece, OrderTest.getOrderNumber());
        order.mrpList.add(mrp2);
        DataBase.insertMRP(OrderTest.getOrderNumber(), production_piece, producing_quantity, production_last_day + 1);

        //int taken_days = allocate_days_to_production(production_last_day, producing_quantity, producing_days, max, production_piece);
        int order_last_day = allocate_days_to_production(production_last_day, producing_quantity, producing_days, max, production_piece);
        productionOrder1.addTransformation(production_last_day, getNumberFromWorkPiece(production_piece), producing_quantity);


        /*
        * Se a peça produzida for P8, alocar também fazer P6
        * Se a peça produzida for P7, alcoar também fazer P4
        * Se a peça produzida for P9, alcoar também fazer P7
        * Se a peça produzida for P5, alcoar também fazer P9
        * */

        if(Objects.equals(production_piece, "P8")){
            int p_day = production_last_day - 1;
            production_piece = "P6";
            production_time = estimatePieceTime(production_piece); // !!
            producing_days = calculateNumberOfPeriods(production_time, producing_quantity);

            MRP mrp3 = new MRP(p_day + 1, producing_quantity, production_piece, OrderTest.getOrderNumber());
            order.mrpList.add(mrp3);
            DataBase.insertMRP(OrderTest.getOrderNumber(), production_piece, producing_quantity, p_day + 1);

            order_last_day = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);
            productionOrder1.addTransformation(p_day, getNumberFromWorkPiece(production_piece), producing_quantity);
        }

        if(Objects.equals(production_piece, "P5")){
            production_piece = "P9";
            //int p_day = production_last_day - taken_days;
            int p_day = production_last_day - 1;
            production_time = estimatePieceTime(production_piece); // !!
            producing_days = calculateNumberOfPeriods(production_time, producing_quantity);

            MRP mrp4 = new MRP(p_day + 1, producing_quantity, production_piece, OrderTest.getOrderNumber());
            order.mrpList.add(mrp4);
            DataBase.insertMRP(OrderTest.getOrderNumber(), production_piece, producing_quantity, p_day + 1);

            order_last_day = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);
            productionOrder1.addTransformation(p_day, getNumberFromWorkPiece(production_piece), producing_quantity);
        }

        if(Objects.equals(production_piece, "P9")){
            production_piece = "P7";

            int x;
            if(Objects.equals(OrderTest.getWorkPiece(), "P9")) {
                x = 1;
            }
            else {
                x = 2;
            }


            int p_day = production_last_day - x;
            production_time = estimatePieceTime(production_piece); // !!
            producing_days = calculateNumberOfPeriods(production_time, producing_quantity);


            MRP mrp5 = new MRP(p_day + 1, producing_quantity, production_piece, OrderTest.getOrderNumber());
            order.mrpList.add(mrp5);
            DataBase.insertMRP(OrderTest.getOrderNumber(), production_piece, producing_quantity, p_day + 1);

            order_last_day = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);
            productionOrder1.addTransformation(p_day, getNumberFromWorkPiece(production_piece), producing_quantity);
        }

        if(Objects.equals(production_piece, "P7")){
            production_piece = "P4";

            int x;
            if(Objects.equals(OrderTest.getWorkPiece(), "P7")) {
                x = 1;
            }
            else if(Objects.equals(OrderTest.getWorkPiece(), "P9")){
                x = 2;
            }
            else {
                x = 3;
            }

            int p_day = production_last_day - x;
            production_time = estimatePieceTime(production_piece); // !!
            producing_days = calculateNumberOfPeriods(production_time, producing_quantity);


            MRP mrp6 = new MRP(p_day + 1, producing_quantity, production_piece, OrderTest.getOrderNumber());
            order.mrpList.add(mrp6);
            DataBase.insertMRP(OrderTest.getOrderNumber(), production_piece, producing_quantity, p_day + 1);

            order_last_day = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);
            productionOrder1.addTransformation(p_day, getNumberFromWorkPiece(production_piece), producing_quantity);
        }

        productionOrders.add(productionOrder1);

        // System.out.println("\n\n\n\n");productionOrder1.printAllTransformations();System.out.println("\n\n\n\n");

        // Purchasing Plan
        /*
         * Stock...
         * Gets quantity
         * Gets number of purchases
         * Gets the day when things need to be order
         * Gets the raw material
         * */

        //int purchase_deliver = production_last_day - taken_days;
        int purchase_deliver = order_last_day;
        String raw = OrderTest.RawMaterial();

        int purchasing_quantity = 0;
        if(Objects.equals(raw, "P1")){
            if(stockClass[purchase_deliver].P1 >= quantity){
                // propagateSubStock(purchase_deliver, quantity, 0);
                purchasing_quantity = 0;
            }
            else{
                purchasing_quantity = quantity - stockClass[purchase_deliver].P1;
                // propagateSubStock(purchase_deliver, stockClass[purchase_deliver].P1, 0);
            }
        }


        if(Objects.equals(raw, "P2")){
            if(stockClass[purchase_deliver].P2 >= quantity){
                // propagateSubStock(purchase_deliver, 0, quantity);
                purchasing_quantity = 0;
            }
            else{
                purchasing_quantity = quantity - stockClass[purchase_deliver].P2;
                // propagateSubStock(purchase_deliver, 0 , stockClass[purchase_deliver].P2);
            }
        }


        int n_orders = num_purchasing(purchasing_quantity);


        if(Objects.equals(raw, "P1")){
            //daysClass[purchase_deliver].setComingP1(n_orders * 4);
            daysClass[purchase_deliver].addComingP1(n_orders * 4);

            propagateAddStock(purchase_deliver, n_orders * 4, 0);
            propagateSubStock(purchase_deliver, quantity, 0);

            if(purchasing_quantity > 0){
                // Create a new PurchasingOrder object
                PurchasingOrder order1 = new PurchasingOrder("Supplier C", n_orders * 4, purchase_deliver - 1, "P1");
                // Add the PurchasingPlan to the list
                purchasingOrders.add(order1);
            }
        }
        if(Objects.equals(raw, "P2")){
            //daysClass[purchase_deliver].setComingP2(n_orders * 4);
            daysClass[purchase_deliver].addComingP2(n_orders * 4);

            propagateAddStock(purchase_deliver, 0, n_orders * 4);
            propagateSubStock(purchase_deliver, 0, quantity);

            if(purchasing_quantity > 0){
                // Create a new PurchasingOrder object
                PurchasingOrder order1 = new PurchasingOrder("Supplier C", n_orders * 4, purchase_deliver - 1, "P2");
                // Add the PurchasingPlan to the list
                purchasingOrders.add(order1);
            }
        }

        order.setStartDate(purchase_deliver);

        DataBase.updateOrderPurchasing(Integer.toString(purchase_deliver - 1), Integer.toString(n_orders*4), OrderTest.getOrderNumber());
        DataBase.updateOrderRealDueDate(Integer.toString(OrderTest.getRealDueDate()), OrderTest.getOrderNumber());
        DataBase.deleteMPS();
        DataBase.insertMPSBatch(daysClass);
        System.out.println("Updating MPS-DB");
    }

    private static int checkTime(Order order) throws CloneNotSupportedException {

        int finishing_day = -1;

        Order OrderTest = order;
        int delivering_day = Integer.parseInt(OrderTest.getDueDate()) - 1;


        while(finishing_day <= Today || (finishing_day <= 0)){

            delivering_day++;
            Day[] clonedDaysClass = cloneDaysClass();


            int quantity = Integer.parseInt(OrderTest.getQuantity());

            // Delivering Plan
            /*
             * 1. Gets the delivering day
             * 2. Gets delivering quantity
             * 3. Gets delivering piece
             * 4. Allocates delivering day
             * */

            int delivering_quantity = quantity;                                     // 2.
            String delivering_piece = OrderTest.getWorkPiece();                     // 3.

            while(clonedDaysClass[delivering_day].totalDelivering() + delivering_quantity > 8){
                delivering_day++;
            }

            clonedDaysClass[delivering_day].setDeliverQuantity(delivering_quantity);      // 4.
            clonedDaysClass[delivering_day].setDeliverPiece(delivering_piece);            // 4.

            // Production Plan
            /*
             * //Gets the number of simple transformations
             * Gets the last day when all production need to be completed
             * Gets quantity
             * Gets estimate production time
             * Gets the number of days to produce
             * Allocate days based on quantity to produce
             * */

            String production_piece = OrderTest.getWorkPiece();
            int production_last_day = delivering_day - 1;
            int producing_quantity = quantity;
            int production_time = estimatePieceTime(production_piece);
            int producing_days = calculateNumberOfPeriods(production_time, producing_quantity);
            int max = tasksIn60Seconds(production_time);

            int order_last_day = allocate_days_to_production_clone(production_last_day, producing_quantity, producing_days, max, production_piece, clonedDaysClass);

            /*
             * Se a peça produzida for P8, alocar também fazer P6
             * Se a peça produzida for P7, alcoar também fazer P4
             * Se a peça produzida for P9, alcoar também fazer P7
             * Se a peça produzida for P5, alcoar também fazer P9
             * */

            if(Objects.equals(production_piece, "P8")){
                int p_day = production_last_day - 1;
                production_piece = "P6";
                production_time = estimatePieceTime(production_piece); // !!
                producing_days = calculateNumberOfPeriods(production_time, producing_quantity);

                order_last_day = allocate_days_to_production_clone(p_day, producing_quantity, producing_days, max, production_piece, clonedDaysClass);
            }

            if(Objects.equals(production_piece, "P5")){
                production_piece = "P9";
                //int p_day = production_last_day - taken_days;
                int p_day = production_last_day - 1;
                production_time = estimatePieceTime(production_piece); // !!
                producing_days = calculateNumberOfPeriods(production_time, producing_quantity);

                order_last_day = allocate_days_to_production_clone(p_day, producing_quantity, producing_days, max, production_piece, clonedDaysClass);
            }

            if(Objects.equals(production_piece, "P9")){
                production_piece = "P7";

                int x;
                if(Objects.equals(OrderTest.getWorkPiece(), "P9")) {
                    x = 1;
                }
                else {
                    x = 2;
                }


                int p_day = production_last_day - x;
                production_time = estimatePieceTime(production_piece); // !!
                producing_days = calculateNumberOfPeriods(production_time, producing_quantity);

                order_last_day = allocate_days_to_production_clone(p_day, producing_quantity, producing_days, max, production_piece, clonedDaysClass);
            }

            if(Objects.equals(production_piece, "P7")){
                production_piece = "P4";

                int x;
                if(Objects.equals(OrderTest.getWorkPiece(), "P7")) {
                    x = 1;
                }
                else if(Objects.equals(OrderTest.getWorkPiece(), "P9")){
                    x = 2;
                }
                else {
                    x = 3;
                }

                int p_day = production_last_day - x;
                production_time = estimatePieceTime(production_piece); // !!
                producing_days = calculateNumberOfPeriods(production_time, producing_quantity);

                order_last_day = allocate_days_to_production_clone(p_day, producing_quantity, producing_days, max, production_piece, clonedDaysClass);
            }

            //System.out.println(order_last_day + ":");


            // Purchasing Plan
            /*
             * Stock...
             * Gets quantity
             * Gets number of purchases
             * Gets the day when things need to be order
             * Gets the raw material
             * */


            int purchase_deliver = order_last_day;
            finishing_day = purchase_deliver - 1;


            //System.out.println("FINISHING DAY: " + finishing_day + " starting day:" + delivering_day);
        }


        return delivering_day;
    }

    private static int getNumberFromWorkPiece(String wp){
        if(Objects.equals(wp, "P3")){
            return 3;
        }
        else if(Objects.equals(wp, "P4")){
            return 4;
        }
        else if(Objects.equals(wp, "P5")){
            return 5;
        }
        else if(Objects.equals(wp, "P6")){
            return 6;
        }
        else if(Objects.equals(wp, "P7")){
            return 7;
        }
        else if(Objects.equals(wp, "P8")){
            return 8;
        }
        else if(Objects.equals(wp, "P9")){
            return 9;
        }

        return 0;
    }


    public static int num_purchasing(int num) {
        return (num + 3) / 4;
    }


    public static int calculateNumberOfPeriods(int taskTimeInSeconds, int numTasks) {
        int totalTimeInSeconds = taskTimeInSeconds * numTasks;
        int secondsPerPeriod = 60;
        int secondsRemaining = totalTimeInSeconds;
        int periodsCompleted = 0;

        while (secondsRemaining > 0) {
            periodsCompleted++;
            if (secondsRemaining >= secondsPerPeriod) {
                secondsRemaining -= secondsPerPeriod;
            } else {
                secondsRemaining = 0;
            }
        }

        return periodsCompleted;
    }

    public static int allocate_days_to_production(int start, int quantity, int days, int max, String wp){
        int i = 0;
        int count = 0;
        while(quantity > 0){

            if(start - i <= 0){
                return 0;
            }

            if((daysClass[start - i].totalPiecesProduced() + max <= 4) && (daysClass[start - i].totalPiecesType(wp) < 2)){
                if(quantity >= max) {
                    daysClass[start - i].addProduction(max, wp);
                    quantity -= max;
                }
                else {
                    daysClass[start - i].addProduction(quantity, wp);
                    quantity -= quantity;
                }
                count++;
            }
            i++;
        }

        return start - i;
    }

    public static int allocate_days_to_production_clone(int start, int quantity, int days, int max, String wp, Day[] Clone){
        int i = 0;
        int count = 0;
        while(quantity > 0){
            //System.out.println("quant. " + quantity + " " + max);
            if(start - i <= 0){
                return 0;
            }

            if((Clone[start - i].totalPiecesProduced() + max <= 4) && (Clone[start - i].totalPiecesType(wp) < 3)){
                if(quantity >= max) {
                    Clone[start - i].addProduction(max, wp);
                    quantity -= max;
                }
                else {
                    Clone[start - i].addProduction(quantity, wp);
                    quantity -= quantity;
                }
                count++;
            }
            i++;
        }

        //System.out.println("-> "+(start-i));
        //return count;
        return start - i;
    }

    public static int tasksIn60Seconds(int taskDuration) {
        return 60 / taskDuration;
    }


    public static void printTaskSchedule(int taskTimeInSeconds, int numTasks, int periodTimeInSeconds) {
        int periodsCompleted = calculateNumberOfPeriods(taskTimeInSeconds, numTasks);
        int totalTasksCompleted = numTasks;
        int tasksPerPeriod = (int) Math.ceil((double) numTasks / periodsCompleted);

        System.out.println("Total periods required: " + periodsCompleted);
        for (int i = 1; i <= periodsCompleted; i++) {
            int tasksCompleted = Math.min(tasksPerPeriod, totalTasksCompleted);
            System.out.println("Period " + i + ": " + tasksCompleted + " tasks completed");
            totalTasksCompleted -= tasksCompleted;
        }
    }





    public static int allocate_days_to_deliver(int start, int quantity, String wp){
        int day = start;
        while (quantity > 0) {
            if (quantity >= 4) {

                // definir tipo de peça
                // atualizar stock
                // System.out.print("4 "); // debug
                daysClass[day].setDeliverQuantity(4);
                quantity -= 4;

            } else {
                // System.out.print(quantity + " "); // debug
                daysClass[day].setDeliverQuantity(quantity);
                quantity = 0;
            }
            day++;
        }
        return day;
    }

    public static int estimatePieceTime(String WorkPiece){
        int transportation_time = 10;
        int transformation_time = 0;

        if(Objects.equals(WorkPiece, "P3")) transformation_time = 10;
        else if(Objects.equals(WorkPiece, "P4")) transformation_time = 10;
        else if(Objects.equals(WorkPiece, "P5")) transformation_time = 15;
        else if(Objects.equals(WorkPiece, "P6")) transformation_time = 20;
        else if(Objects.equals(WorkPiece, "P7")) transformation_time = 10;
        else if(Objects.equals(WorkPiece, "P8")) transformation_time = 30;
        else if(Objects.equals(WorkPiece, "P9")) transformation_time = 10;

        return transformation_time + transportation_time;
    }

    private static void propagateAddStock(int starting_day, int P1, int P2){
        for(int i = starting_day; i < 100; i++){
            stockClass[i].addP1(P1);
            stockClass[i].addP2(P2);
        }
    }

    private static void propagateSubStock(int day, int P1, int P2){
        for(int i = 0; i < 100; i++){
            if(stockClass[i].P1 > 0){
                stockClass[i].subP1(P1);
            }
            if(stockClass[i].P2 > 0){
                stockClass[i].subP2(P2);
            }
        }
    }

    private static void saveMPS(){
        for (int i = 0; i < daysClass.length; i++) {
            DataBase.insertMPS(i, daysClass[i].getComingP1(), daysClass[i].getComingP2(),
                    daysClass[i].getProductionP3(), daysClass[i].getProductionP4(), daysClass[i].getProductionP5(), daysClass[i].getProductionP6(), daysClass[i].getProductionP7(), daysClass[i].getProductionP8(), daysClass[i].getProductionP9(),
                    daysClass[i].getDeliverP3(), daysClass[i].getDeliverP4(), daysClass[i].getDeliverP5(), daysClass[i].getDeliverP6(), daysClass[i].getDeliverP7(), daysClass[i].getDeliverP8(), daysClass[i].getDeliverP9());
        }
    }

    public static void UpdateProductionPlan(int day, int transformation, int time){
        for(ProductionOrder p : productionOrders){
            p.getTimeForDayTransformation(day, transformation, time);
        }

        for(Order order : orders){
            String OrderNumber = order.getOrderNumber();
            for(ProductionOrder p : productionOrders){
                if (Objects.equals(OrderNumber, p.OrderNumber)){
                    System.out.println("LEL-" + p.totalTime);
                    order.TotalTimeInProduction += p.totalTime;
                }
            }
        }
    }
}
