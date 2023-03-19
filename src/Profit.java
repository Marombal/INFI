/*
* Tc = Rc + Pc + Dc
* Dc = Rc * (Dd - Ad) * 0.01
* Pc = 1 * Pt
*
* Tc – Total Cost
* Rc – Raw Material Cost (price of the raw material used to produce that piece) Pc – Production Cost (Cost to Produce the piece)
* Dc – Depreciation Cost (Cost of money invested in the piece)
* Ad – Arrival Date – date the raw material arrived at the production line
* Dd – Dispatch Date – date final work-piece leaves the production line (unloaded on cell E)
* Pt – Total Production time (in seconds)
* */



public class Profit extends Thread {

    float Tc, Rc, Pc, Dc, Dd, Ad, Pt;

    @Override
    public void run() {
        while(true){

            System.out.println("Calculation Profits....");
            System.out.println("Total cost: " + Tc);

            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {}
        }
    }
}