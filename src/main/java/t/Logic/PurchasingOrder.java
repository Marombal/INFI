package t.Logic;

public class PurchasingOrder {
    public String Supplier;

    public int quantity;
    public int purchasing_day;
    public String WorkPiece;

    PurchasingOrder(String s, int q, int p, String wp){
        this.Supplier = s;
        this.quantity = q;
        this.purchasing_day = p;
        this.WorkPiece = wp;
    }
}
