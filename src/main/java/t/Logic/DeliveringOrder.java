package t.Logic;

public class DeliveringOrder {
    public int Day;
    public String WorkPiece;
    public int Quantity;

    public String Client;

    public String OrderNumber;

    DeliveringOrder(int d, String wp, int q, String n){
        this.Day = d;
        this.WorkPiece = wp;
        this.Quantity = q;
        this.OrderNumber = n;
    }
}
