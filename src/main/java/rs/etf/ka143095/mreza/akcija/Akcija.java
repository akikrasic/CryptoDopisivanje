package rs.etf.ka143095.mreza.akcija;

import rs.etf.ka143095.crypto.Kripto;
import rs.etf.ka143095.main.Main;
import rs.etf.ka143095.mreza.Klijent;

/**
 * Created by aki on 7/7/16.
 */
public class Akcija {
    public static final int ZAHTEV_ZA_USPOSTAVLJANJE_VEZE=0;
    public static final int KRIPTOVANJE_SIMETRICNOG_KLJUCA =1;
    public static final int SLANJE_PORUKE=2;
    public static final int ZAVRSETAK_KOMUNIKACIJE=3;

    public Akcija(Main main){
        this.main=main;
        kripto = main.getKripto();
        klijent = main.getKlijent();
    }


    public  void izvrsiteAkciju(String poruka){}
    protected Main main;
    protected Kripto kripto;
    protected Klijent klijent;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
