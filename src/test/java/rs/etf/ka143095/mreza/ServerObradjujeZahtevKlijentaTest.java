package rs.etf.ka143095.mreza;

import org.junit.Before;
import org.junit.Test;
import rs.etf.ka143095.main.Main;
import rs.etf.ka143095.mreza.akcija.*;

import static org.junit.Assert.*;

/**
 * Created by aki on 7/7/16.
 */
public class ServerObradjujeZahtevKlijentaTest {
    private ServerObradjujeZahtevKlijenta obrada;
    private TransferObjekat t;
    private Main main;
    @Before
    public void setUp() throws Exception {
        main = new Main();
        obrada = new ServerObradjujeZahtevKlijenta(null,main);//null za socket
        t = new TransferObjekat();

    }
    @Test
    public void proveraObjekataAkcija(){
        Akcija[] akcije = obrada.getAkcije();
        Class[] klase = {
                ZahtevZaUspostavljanjeVeze.class,
                KriptovanjeSimetricnogKljuca.class,
                SlanjePoruke.class,
                ZavrsetakKomunikacije.class,

        };
        for(int i=0;i<akcije.length;i++)
        assertEquals(akcije[i].getClass(),klase[i] );
    }
    @Test
    public void daLiRadiFunkcijaZaObradu(){
        TransferObjekat t = new TransferObjekat();
        t.id= Akcija.ZAHTEV_ZA_USPOSTAVLJANJE_VEZE;
        t.poruka="abc";
        obrada.obradaPrimljenogTransferObjekta(t);
    }
    @Test
    public void proveraZavrsetkaKomunikacije(){
        obrada.getAkcije()[Akcija.ZAVRSETAK_KOMUNIKACIJE]= new ZavrsetakKomunikacije(main, obrada){
            public void obavestavanjeKorisnikaFormeDaJeGotovaKomunikacija(){

            }
        };
        t.id=Akcija.ZAVRSETAK_KOMUNIKACIJE;
        obrada.obradaPrimljenogTransferObjekta(t);
        assertFalse(main.getServer().isNijeZavrsenaAplikacija());
    }



}