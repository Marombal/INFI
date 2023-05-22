package t.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionOrder {
    public String Client;
    public String OrderNumber;

    ProductionOrder(String client, String number){
        this.Client = client;
        this.OrderNumber = number;
    }

    Map<Integer, Map<Integer, Integer>> productionPlan = new HashMap<>();

    public void addTransformation(int day, int transformation, int quantity){
        productionPlan.computeIfAbsent(day, k -> new HashMap<>()).put(transformation, quantity);
    }

    public void accessTransformation(int day) {
        Map<Integer, Integer> transformations = productionPlan.get(day);
        if (transformations != null) {
            // Process the transformations for the day
            for (Map.Entry<Integer, Integer> entry : transformations.entrySet()) {
                int transformation = entry.getKey();
                int quantity = entry.getValue();
                // Do something with the transformation and quantity
            }
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


}
