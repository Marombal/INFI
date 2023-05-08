package t.Logic;

public class Stock {

    public Stock(int Day){
        day = Day;
    }

    static private int day;
    public int P1 = 0;
    public int P2 = 0;

    public void addP1(int quantity){
        P1 += quantity;
    }

    public void addP2(int quantity){
        P2 += quantity;
    }

    public void subP1(int quantity){
        P1 -= quantity;
    }
    public void subP2(int quantity){
        P2 -= quantity;
    }



    public int P3 = 0;
    public int P4 = 0;
    public int P5 = 0;
    public int P6 = 0;
    public int P7 = 0;
    public int P8 = 0;
    public int P9 = 0;

    public void printStock(){
        System.out.println("Available Stock...");
        System.out.println("P1: " + this.P1);
        System.out.println("P2: " + this.P2);
    }
}
