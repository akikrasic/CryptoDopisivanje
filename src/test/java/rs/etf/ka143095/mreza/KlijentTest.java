package rs.etf.ka143095.mreza;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import rs.etf.ka143095.main.Main;

import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by aki on 7/7/16.
 */
public class KlijentTest {
    private Main main;
    private Klijent klijent;

    public void SetUp(){
      klijent = new Klijent(null){
          public void posaljite(TransferObjekat t){
            //da se ne bi nista slalo preko soketa, kako bi sve moglo da funkcionise
          }
      };
    }


    @Test
    public void notNullMainkripto(){
        Main main = new Main();
        assertNotNull(main.getKlijent().getKripto());
    }
}
