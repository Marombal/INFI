/*
* 1. Create MPS
* 2. Checks DB if there is any unfinished order
*   2.1. If YES, add unfinished orders to MPS
* 3. Launches UDP Listener (multithreading)
*
* */


import java.util.List;

public class ERP {

    /* 1. Create MPS */
    //public static MPS mps;

    public static void main(String[] args){

        /* 2. Checks DB if there is any unfinished order */

        DBUpdater myDBUpdater = new DBUpdater();
        myDBUpdater.start();


        //
        UDPListener myUDPListener = new UDPListener();
        myUDPListener.start();

        //
        MPS myMPS = new MPS();
        myMPS.start();

        //
        Profit myProfit = new Profit();
        myProfit.start();

    }
}
