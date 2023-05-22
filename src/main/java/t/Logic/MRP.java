package t.Logic;

public class MRP {
    private int day;
    private int quantity;
    private String materialType;
    private String orderNumber;

    public MRP(int day, int quantity, String materialType, String orderNumber) {
        this.day = day;
        this.quantity = quantity;
        this.materialType = materialType;
        this.orderNumber = orderNumber;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void printData() {
        System.out.println("Order Number: " + orderNumber);
        System.out.println("Material Type: " + materialType);
        System.out.println("Quantity: " + quantity);
        System.out.println("Day: " + day);
    }
}
