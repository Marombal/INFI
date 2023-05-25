package t.Logic;

import java.util.Objects;

public class PurchasingOrder {
    public String Supplier;

    public int quantity;
    public int purchasing_day;
    public String WorkPiece;

    PurchasingOrder(String s, int q, int p, String wp){
        this.Supplier = s;
        this.quantity = q;
        this.purchasing_day = p;

        if(Objects.equals(wp, "P3")) this.WorkPiece = "P2";
        else if(Objects.equals(wp, "P4")) this.WorkPiece = "P2";
        else if(Objects.equals(wp, "P5")) this.WorkPiece = "P2";
        else if(Objects.equals(wp, "P6")) this.WorkPiece = "P1";
        else if(Objects.equals(wp, "P7")) this.WorkPiece = "P2";
        else if(Objects.equals(wp, "P8")) this.WorkPiece = "P1";
        else if(Objects.equals(wp, "P9")) this.WorkPiece = "P2";
    }
}
