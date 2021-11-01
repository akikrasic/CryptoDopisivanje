package rs.etf.ka143095.mreza.akcija;

import org.junit.Test;
import rs.etf.ka143095.main.Main;
import rs.etf.ka143095.mreza.Klijent;
import rs.etf.ka143095.mreza.TransferObjekat;

import static org.junit.Assert.*;

/**
 * Created by aki on 7/8/16.
 */
public class ZahtevZaUspostavljanjeVezeTest {
    @Test
    public void razdvojitePoruku(){
        String poruka ="192.168.1.4/50000/asdf";
        int prvaCrta=poruka.indexOf("/");
        String adresa = poruka.substring(0,prvaCrta);
        assertEquals(adresa, "192.168.1.4");
        System.out.println(poruka);
        int drugaCrta = poruka.indexOf("/", prvaCrta+1);
        System.out.println(drugaCrta);
        String port = poruka.substring(prvaCrta+1,drugaCrta);
        String kljuc = poruka.substring(drugaCrta+1);
        assertEquals(port, "50000");
        assertEquals (kljuc, "asdf");

    }

}