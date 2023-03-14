public class Order {
    private int Quantity;
    private int WorkPiece;
    private int OrderNumber;
    private int DueDate;
    private int LatePen;
    private int EarlyPen;
    String Client;

    Order(){}

    Order(int quantity,
          int workPiece,
          int orderNumber,
          int dueDate,
          int latePen,
          int earlyPen){

        Quantity = quantity;
        WorkPiece = workPiece;
        OrderNumber = orderNumber;
        DueDate = dueDate;
        LatePen = latePen;
        EarlyPen = earlyPen;

    }

    void setQuantity(int q){
        Quantity = q;
    }

    void setWorkPiece(int w){
        WorkPiece = w;
    }

    void setOrderNumber(int o){
        OrderNumber = o;
    }

    void setDueDate(int d){
        DueDate = d;
    }

    void setLatePen(int l){
        LatePen = l;
    }

    void setEarlyPen(int e){
        EarlyPen = e;
    }

}
