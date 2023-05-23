package t.Logic;

import java.util.Objects;

public class Day implements Cloneable{

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


    private int deliverP3 = 0;
    private int deliverP4 = 0;
    private int deliverP5 = 0;
    private int deliverP6 = 0;
    private int deliverP7 = 0;
    private int deliverP8 = 0;
    private int deliverP9 = 0;

    private int typesDeliver = 0;



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

    public int getTypesDeliver() {
        return typesDeliver;
    }

    public int getDeliverP3() {
        return deliverP3;
    }

    public int getDeliverP4() {
        return deliverP4;
    }

    public int getDeliverP5() {
        return deliverP5;
    }

    public int getDeliverP6() {
        return deliverP6;
    }

    public int getDeliverP7() {
        return deliverP7;
    }

    public int getDeliverP8() {
        return deliverP8;
    }

    public int getDeliverP9() {
        return deliverP9;
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

    public void setDeliverP3(int deliverP3) {
        this.deliverP3 = deliverP3;
    }

    public void setDeliverP4(int deliverP4) {
        this.deliverP4 = deliverP4;
    }

    public void setDeliverP5(int deliverP5) {
        this.deliverP5 = deliverP5;
    }

    public void setDeliverP6(int deliverP6) {
        this.deliverP6 = deliverP6;
    }

    public void setDeliverP7(int deliverP7) {
        this.deliverP7 = deliverP7;
    }

    public void setDeliverP8(int deliverP8) {
        this.deliverP8 = deliverP8;
    }

    public void setDeliverP9(int deliverP9) {
        this.deliverP9 = deliverP9;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    public void addProduction(int quantity, String WorkPiece){
        if(Objects.equals("P3", WorkPiece)){
            productionP3 += quantity;
        }
        else if(Objects.equals("P4", WorkPiece)){
            productionP4 += quantity;
        }
        else if(Objects.equals("P5", WorkPiece)){
            productionP5 += quantity;
        }
        else if(Objects.equals("P6", WorkPiece)){
            productionP6 += quantity;
        }
        else if(Objects.equals("P7", WorkPiece)){
            productionP7 += quantity;
        }
        else if(Objects.equals("P8", WorkPiece)){
            productionP8 += quantity;
        }
        else if(Objects.equals("P9", WorkPiece)){
            productionP9 += quantity;
        }

        countTypesProducing();
    }

    public void addDelivering(int quantity, String WorkPiece){
        if(Objects.equals("P3", WorkPiece)){
            deliverP3 += quantity;
        }
        else if(Objects.equals("P4", WorkPiece)){
            deliverP4 += quantity;
        }
        else if(Objects.equals("P5", WorkPiece)){
            deliverP5 += quantity;
        }
        else if(Objects.equals("P6", WorkPiece)){
            deliverP6 += quantity;
        }
        else if(Objects.equals("P7", WorkPiece)){
            deliverP7 += quantity;
        }
        else if(Objects.equals("P8", WorkPiece)){
            deliverP8 += quantity;
        }
        else if(Objects.equals("P9", WorkPiece)){
            deliverP9 += quantity;
        }

        //countTypesProducing();
    }

    public void addComingP1(int comingP1) {
        this.comingP1 += comingP1;
    }

    public void addComingP2(int comingP2) {
        this.comingP2 += comingP2;
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

    public void countTypesDelivering() {
        int count = 0;
        if (deliverP3 > 0) {
            count++;
        }
        if (deliverP4 > 0) {
            count++;
        }
        if (deliverP5 > 0) {
            count++;
        }
        if (deliverP6 > 0) {
            count++;
        }
        if (deliverP7 > 0) {
            count++;
        }
        if (deliverP8 > 0) {
            count++;
        }
        if (deliverP9 > 0) {
            count++;
        }

        typesDeliver = count;
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
        //System.out.println("deliverQuantity: " + this.deliverQuantity);
        //System.out.println("deliverPiece:" + this.deliverPiece);
        System.out.println("Delivering\n");
        System.out.println("P3: " + this.deliverP3);
        System.out.println("P4: " + this.deliverP4);
        System.out.println("P5: " + this.deliverP5);
        System.out.println("P6: " + this.deliverP6);
        System.out.println("P7: " + this.deliverP7);
        System.out.println("P8: " + this.deliverP8);
        System.out.println("P9: " + this.deliverP9);
    }

    public String sendDay(){

        int delivering_piece = getPNumber(deliverPiece);

        String generatedString = String.format("DPP-D%dPU%d%dPR%d%d%d%d%d%d%dD%d%d-DPP",
                day,
                comingP1, comingP2,
                productionP3, productionP4, productionP5, productionP6, productionP7, productionP8, productionP9,
                delivering_piece, deliverQuantity);

        String generatedStringEz = String.format("D%dPU%d%dPR%d%d%d%d%d%d%dD%d%d%d%d%d%d%d",
                day,
                comingP1, comingP2,
                productionP3, productionP4, productionP5, productionP6, productionP7, productionP8, productionP9,
                deliverP3, deliverP4, deliverP5, deliverP6, deliverP7, deliverP8, deliverP9);

        //System.out.println(generatedStringEz);

        return generatedStringEz;
    }


    private int getPNumber(String input) {
        if (input.charAt(0) == 'P' && input.charAt(1) >= '1' && input.charAt(1) <= '9') {
            return Integer.parseInt(input.substring(1));
        } else {
            return 0;
        }
    }

    public int totalPiecesProduced(){
        return (productionP3 + productionP4 + productionP5 + productionP6 + productionP7 + productionP8 + productionP9);
    }

    public int totalPiecesType(String WorkPiece){
        if(Objects.equals("P3", WorkPiece)){
            return productionP3;
        }
        else if(Objects.equals("P4", WorkPiece)){
            return productionP4;
        }
        else if(Objects.equals("P5", WorkPiece)){
            return productionP5;
        }
        else if(Objects.equals("P6", WorkPiece)){
            return productionP6;
        }
        else if(Objects.equals("P7", WorkPiece)){
            return productionP7;
        }
        else if(Objects.equals("P8", WorkPiece)){
            return productionP8;
        }
        else if(Objects.equals("P9", WorkPiece)){
            return productionP9;
        }

        return -1;
    }

    public String production(){
        String Production = "";

        if(productionP3 > 0){
            Production = Production + " P3:" + productionP3;
        }
        if(productionP4 > 0){
            Production = Production + " P4:" + productionP4;
        }
        if(productionP5 > 0){
            Production = Production + " P5:" + productionP5;
        }
        if(productionP6 > 0){
            Production = Production + " P6:" + productionP6;
        }
        if(productionP7 > 0){
            Production = Production + " P7:" + productionP7;
        }
        if(productionP8 > 0){
            Production = Production + " P8:" + productionP8;
        }
        if(productionP9 > 0){
            Production = Production + " P9:" + productionP9;
        }

        return Production;
    }
}
