package rs.etf.ka143095.mreza;

import org.junit.Before;
import org.junit.Test;
import rs.etf.ka143095.main.Main;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by aki on 7/7/16.
 */
public class ServerKlijentTest {
    private Main app1;
    private Main app2;
    private int port1;
    private int port2;
    @Before
    public void init(){

        app1 = new Main();
        app2 = new Main(port2);

    }
    @Test
    public void notNull(){
        assertNotNull(app1.getServer());


    }

    @Test
    public void notNullDrugi(){
        assertNotNull(app2.getServer());

    }


    public void posaljiteNajprostije(){
        app2.getKlijent().uspostaviteVezu(IpAdresa.DEFAULT_IP_ZA_TESTIRANJE, port1);
        app2.getKlijent().posaljite(new TransferObjekat(){
            {
                poruka = "radi";
            }
        });

    }



    @Test
    public void povezivanjeServeraIKlijentaIspravno(){


    }

}