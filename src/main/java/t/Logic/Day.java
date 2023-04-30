package t.Logic;

import java.util.Objects;

public class Day {

    private int day;

    private int[] virtualStock = new int[9];

    private int comingP1 = 0;
    private int comingP2 = 0;

    private int productionP3 = 0;
    private int productionP4 = 0;
    private int productionP5 = 0;
    private int productionP6 = 0;
    private int productionP7 = 0;
    private int productionP8 = 0;
    private int productionP9 = 0;
    private int typesProducing = 0;

    private int deliverQuantity = 0;
    private String deliverPiece = " ";




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

    public String getDeliverPiece() {
        return deliverPiece;
    }

    public int getDeliverQuantity() {
        return deliverQuantity;
    }

    public int[] getVirtualStock() {
        return virtualStock;
    }

    public int getProductionP3() {
        return productionP3;
    }

    public int getProductionP4() {
        return productionP4;
    }

    public int getProductionP5() {
        return productionP5;
    }

    public int getProductionP6() {
        return productionP6;
    }

    public int getProductionP7() {
        return productionP7;
    }

    public int getProductionP8() {
        return productionP8;
    }

    public int getProductionP9() {
        return productionP9;
    }

    public int getTypesProducing() {
        return typesProducing;
    }

    public void setComingP1(int comingP1) {
        this.comingP1 = comingP1;
    }

    //public void setDay(int day) {
      //  this.day = day;
    //}

    public void setComingP2(int comingP2) {
        this.comingP2 = comingP2;
    }

    public void setDeliverQuantity(int deliverQuantity) {
        this.deliverQuantity = deliverQuantity;
    }

    public void setDeliverPiece(String deliverPiece) {
        this.deliverPiece = deliverPiece;
    }

    public void setVirtualStock(int[] virtualStock) {
        this.virtualStock = virtualStock;
    }

    public void setProduction(int quantity, String WorkPiece){
        if(Objects.equals("P3", WorkPiece)){
            setProductionP3(quantity);
        }
        else if(Objects.equals("P4", WorkPiece)){
            setProductionP4(quantity);
        }
        else if(Objects.equals("P5", WorkPiece)){
            setProductionP5(quantity);
        }
        else if(Objects.equals("P6", WorkPiece)){
            setProductionP6(quantity);
        }
        else if(Objects.equals("P7", WorkPiece)){
            setProductionP7(quantity);
        }
        else if(Objects.equals("P8", WorkPiece)){
            setProductionP8(quantity);
        }
        else if(Objects.equals("P9", WorkPiece)){
            setProductionP9(quantity);
        }

        countTypesProducing();
    }

    public void setProductionP3(int productionP3) {
        this.productionP3 = productionP3;
    }

    public void setProductionP4(int productionP4) {
        this.productionP4 = productionP4;
    }

    public void setProductionP5(int productionP5) {
        this.productionP5 = productionP5;
    }

    public void setProductionP6(int productionP6) {
        this.productionP6 = productionP6;
    }

    public void setProductionP7(int productionP7) {
        this.productionP7 = productionP7;
    }

    public void setProductionP8(int productionP8) {
        this.productionP8 = productionP8;
    }

    public void setProductionP9(int productionP9) {
        this.productionP9 = productionP9;
    }

    public void countTypesProducing() {
        int count = 0;
        if (productionP3 > 0) {
            count++;
        }
        if (productionP4 > 0) {
            count++;
        }
        if (productionP5 > 0) {
            count++;
        }
        if (productionP6 > 0) {
            count++;
        }
        if (productionP7 > 0) {
            count++;
        }
        if (productionP8 > 0) {
            count++;
        }
        if (productionP9 > 0) {
            count++;
        }
        typesProducing = count;
    }



    public void printDay(){
        System.out.println("\n--- Day ---");
        // day number
        System.out.println("DAY: " + this.day);
        // Purchasing
        System.out.println("comingP1: " + this.comingP1);
        System.out.println("comingP2:" + this.comingP2);
        // Production
        System.out.println("Production\n");
        System.out.println("P3: " + this.productionP3);
        System.out.println("P4: " + this.productionP4);
        System.out.println("P5: " + this.productionP5);
        System.out.println("P6: " + this.productionP6);
        System.out.println("P7: " + this.productionP7);
        System.out.println("P8: " + this.productionP8);
        System.out.println("P9: " + this.productionP9);

        // Delivering
        System.out.println("deliverQuantity: " + this.deliverQuantity);
        System.out.println("deliverPiece:" + this.deliverPiece);
    }

    public String sendDay(){

        int delivering_piece = getPNumber(deliverPiece);

        String generatedString = String.format("DPP-D%dPU%d%dPR%d%d%d%d%d%d%dD%d%d-DPP",
                day,
                comingP1, comingP2,
                productionP3, productionP4, productionP5, productionP6, productionP7, productionP8, productionP9,
                delivering_piece, deliverQuantity);

        //System.out.println("DP - " + delivering_piece);
        //System.out.println("DQ - " + deliverQuantity);
        System.out.println(generatedString);

        return generatedString;
    }


    private int getPNumber(String input) {
        if (input.charAt(0) == 'P' && input.charAt(1) >= '1' && input.charAt(1) <= '9') {
            return Integer.parseInt(input.substring(1));
        } else {
            return 0;
        }
    }

}
