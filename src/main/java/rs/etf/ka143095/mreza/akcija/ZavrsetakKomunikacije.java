package rs.etf.ka143095.mreza.akcija;

import rs.etf.ka143095.main.Main;
import rs.etf.ka143095.mreza.ServerObradjujeZahtevKlijenta;

/**
 * Created by aki on 7/7/16.
 */
public class ZavrsetakKomunikacije extends Akcija {
    private ServerObradjujeZahtevKlijenta obrada;
    public ZavrsetakKomunikacije(Main main, ServerObradjujeZahtevKlijenta obrada){
        super(main);
        this.obrada = obrada;
    }
    @Override
    public void izvrsiteAkciju(String poruka) {
        this.obavestavanjeKorisnikaFormeDaJeGotovaKomunikacija();
        main.getServer().setNijeZavrsenaAplikacija(false);
        main.getServer().zatvoriteKonekciju();

    }

    public void obavestavanjeKorisnikaFormeDaJeGotovaKomunikacija(){
        main.getForma().krajKomunikacije();
    }

}
