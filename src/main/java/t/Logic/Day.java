package t.Logic;

public class Day {

    private int day;

    private int[] virtualStock = new int[9];

    private int comingP1;
    private int comingP2;

    private int deliverQuantity;
    private int deliverPiece;


    public Day(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public int getComingP1() {
        return comingP1;
    }

    public int getComingP2() {
        return comingP2;
    }

    public int getDeliverPiece() {
        return deliverPiece;
    }

    public int getDeliverQuantity() {
        return deliverQuantity;
    }

    public int[] getVirtualStock() {
        return virtualStock;
    }

    public void setComingP1(int comingP1) {
        this.comingP1 = comingP1;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setComingP2(int comingP2) {
        this.comingP2 = comingP2;
    }

    public void setDeliverQuantity(int deliverQuantity) {
        this.deliverQuantity = deliverQuantity;
    }

    public void setDeliverPiece(int deliverPiece) {
        this.deliverPiece = deliverPiece;
    }

    public void setVirtualStock(int[] virtualStock) {
        this.virtualStock = virtualStock;
    }
}