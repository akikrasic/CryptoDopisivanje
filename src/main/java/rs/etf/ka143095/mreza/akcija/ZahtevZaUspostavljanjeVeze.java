package rs.etf.ka143095.mreza.akcija;

import rs.etf.ka143095.main.Main;

/**
 * Created by aki on 7/7/16.
 */
public class ZahtevZaUspostavljanjeVeze extends Akcija {

    public ZahtevZaUspostavljanjeVeze(Main main){
        super(main);

    }

    @Override
    public void izvrsiteAkciju(String poruka) {

        int prvaCrta=poruka.indexOf("/");
        String adresa = poruka.substring(0,prvaCrta);
        int drugaCrta = poruka.indexOf("/", prvaCrta+1);
        Integer port =Integer.parseInt( poruka.substring(prvaCrta+1,drugaCrta));
        String javniKljucEnkodovani = poruka.substring(drugaCrta+1);
        new Thread(()->{main.getKlijent().uspostaviteVezu(adresa, port);
        kripto.postaviteJavniKljucSagovornika(javniKljucEnkodovani);
        klijent.posaljiteKriptovaniSimetricniKljuc();
        }).start();
    }
}
