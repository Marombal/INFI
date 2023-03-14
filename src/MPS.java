/*
* Master Production Schedule
*
* Should define for each day in the future
* • the amount of work-pieces of each type you expect to have by the end of that day
* • the capacity used in each warehouse
*
* */
public class MPS {

    String Number, PieceInitial, PieceFinal, Quantity;
    public void sendTodayMPS(){
        String msg = "!" + Number + PieceInitial + PieceFinal + Quantity;
    }
}
