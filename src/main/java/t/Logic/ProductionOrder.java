package t.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionOrder {
    public String Client;
    public String OrderNumber;

    public int totalTime = 0;

    ProductionOrder(String client, String number){
        this.Client = client;
        this.OrderNumber = number;
    }

    Map<Integer, Map<Integer, Integer>> productionPlan = new HashMap<>();

    public void addTransformation(int day, int transformation, int quantity){
        productionPlan.computeIfAbsent(day, k -> new HashMap<>()).put(transformation, quantity);
    }

    public int checkDayExists(int day) {
        if (productionPlan.containsKey(day)) {
            return 1; // Day exists in the production plan
        } else {
            return 0; // Day does not exist in the production plan
        }
    }

    public int checkDayTransformationExists(int day, int transformation) {
        Map<Integer, Integer> transformations = productionPlan.get(day);
        if (transformations != null && transformations.containsKey(transformation)) {
            return 1; // Day and transformation exist in the production plan
        } else {
            return 0; // Day or transformation does not exist in the production plan
        }
    }

    public int getTimeForDayTransformation(int day, int transformation, int time) {
        Map<Integer, Integer> transformations = productionPlan.get(day);
        if (transformations != null && transformations.containsKey(transformation)) {
            int quantity = transformations.get(transformation);
            if (quantity > 0) {
                totalTime+=time;
                System.out.println("totaltime+ "+totalTime);
                return time / quantity; // Return the calculated time divided by quantity
            } else {
                return 0; // Return 0 if quantity is 0
            }
        } else {
            System.out.println("PR Nao existe");
            return 0; // Return 0 if day or transformation does not exist in the production plan
        }
    }


    public void printAllTransformations() {
        for (Map.Entry<Integer, Map<Integer, Integer>> dayEntry : productionPlan.entrySet()) {
            int day = dayEntry.getKey();
            System.out.println("Day " + day + " transformations:");

            Map<Integer, Integer> transformations = dayEntry.getValue();
            for (Map.Entry<Integer, Integer> transformationEntry : transformations.entrySet()) {
                int transformation = transformationEntry.getKey();
                int quantity = transformationEntry.getValue();
                System.out.println("Transformation: " + transformation + ", Quantity: " + quantity);
            }

            System.out.println(); // Add a blank line for separation
        }
    }




    public void parseMessage(String message) {
        if (message.length() < 15) {
            System.out.println("Invalid message format!");
            return;
        }

        // Extract the day from the message
        String dayString = message.substring(1, 3);
        int day = Integer.parseInt(dayString);

        // Extract the time taken for each piece type
        int[] timeTaken = new int[7];
        for (int i = 0; i < 7; i++) {
            String timeString = message.substring(5 + i * 2, 7 + i * 2);
            int time = Integer.parseInt(timeString);
            timeTaken[i] = time;
        }

        // Print the parsed values
        System.out.println("Day: " + day);
        for (int i = 0; i < 7; i++) {
            System.out.println("Time taken for piece type " + (i + 3) + ": " + timeTaken[i] + " seconds");
        }
    }

}
