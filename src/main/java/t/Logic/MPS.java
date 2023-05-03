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

    public static Day[] daysClass = new Day[100];


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
        for (int i = 0; i < 100; i++) {
            daysClass[i] = new Day(i);
        }
    }

    public static void print20days(){
        for (int i = 0; i < 20; i++) {
            daysClass[i].printDay();
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

    public static void updateMPS4 (Order order){
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
        int delivering_day = Integer.parseInt(OrderTest.getDueDate());          // 1.
        int delivering_quantity = quantity;                                     // 2.
        String delivering_piece = OrderTest.getWorkPiece();                     // 3.

        daysClass[delivering_day].setDeliverQuantity(delivering_quantity);      // 4.
        daysClass[delivering_day].setDeliverPiece(delivering_piece);            // 4.

        // Production Plan
        /*
        * //Gets the number of simple transformations
        * Gets the last day when all production need to be completed
        * Gets quantity
        * Gets estimate production time
        * Gets the number of days to produce
        * Allocate days based on quantity to produce
        * */

        int production_last_day = delivering_day - 1;
        int producing_quantity = quantity;
        int production_time = 25;
        int producing_days = calculateNumberOfPeriods(production_time, producing_quantity);
        int max = tasksIn60Seconds(production_time);
        String production_piece = OrderTest.getWorkPiece();

        //int taken_days = allocate_days_to_production(production_last_day, producing_quantity, producing_days, max, production_piece);
        int order_last_day = allocate_days_to_production(production_last_day, producing_quantity, producing_days, max, production_piece);

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

            //max = tasksIn60Seconds(production_time);

            //int taken_days_aux = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);

            //taken_days += taken_days_aux;

            order_last_day = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);
        }

        if(Objects.equals(production_piece, "P5")){
            production_piece = "P9";
            //int p_day = production_last_day - taken_days;
            int p_day = production_last_day - 1;
            production_time = estimatePieceTime(production_piece); // !!
            producing_days = calculateNumberOfPeriods(production_time, producing_quantity);

            // max = tasksIn60Seconds(production_time);

            //int taken_days_aux = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);
            //taken_days += taken_days_aux;

            order_last_day = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);
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

            //max = tasksIn60Seconds(production_time);

            //int taken_days_aux = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);
            //taken_days += taken_days_aux;

            order_last_day = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);
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

            //max = tasksIn60Seconds(production_time);

            //int taken_days_aux = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);
            //taken_days += taken_days_aux;

            order_last_day = allocate_days_to_production(p_day, producing_quantity, producing_days, max, production_piece);
        }

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
            if(Stock.P1 >= quantity){
                Stock.P1 -= quantity;
                purchasing_quantity = 0;
            }
            else{
                purchasing_quantity = quantity - Stock.P1;
                Stock.P1 = 0;
            }
        }


        if(Objects.equals(raw, "P2")){
            if(Stock.P2 >= quantity){
                Stock.P2 -= quantity;
                purchasing_quantity = 0;
            }
            else{
                purchasing_quantity = quantity - Stock.P2;
                Stock.P2 = 0;
            }
        }

        int n_orders = num_purchasing(purchasing_quantity);


        if(Objects.equals(raw, "P1")){
            daysClass[purchase_deliver].setComingP1(n_orders * 4);
        }
        if(Objects.equals(raw, "P2")){
            daysClass[purchase_deliver].setComingP2(n_orders * 4);
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
            if((daysClass[start - i].totalPiecesProduced() + max <= 6) && (daysClass[start - i].totalPiecesType(wp) < 2)){
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

}
