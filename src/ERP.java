
public class ERP {

    public static void main(String[] args){

        /* Checks DB if there is any unfinished order */
        DBUpdater myDBUpdater = new DBUpdater();
        myDBUpdater.start();

        /* Listener UDP to listen to Clients and add orders to DataBase */
        UDPListener myUDPListener = new UDPListener();
        myUDPListener.start();

        /* Process MPS */
        MPS myMPS = new MPS();
        myMPS.start();

        /* Calculate Profits */
        Profit myProfit = new Profit();
        myProfit.start();

    }

}
