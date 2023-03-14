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
        System.out.println(OrderNumber + Quantity + WorkPiece);
    }
}
