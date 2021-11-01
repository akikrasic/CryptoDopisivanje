package rs.etf.ka143095.mreza.akcija;

import org.junit.Test;
import rs.etf.ka143095.main.Main;

import static org.junit.Assert.*;

/**
 * Created by aki on 7/7/16.
 */
public class AkcijaTest {


    @Test
    public void probaDaLiRadi(){
        Akcija a = new Akcija(new Main()){
            public void izvrsiteAkciju(String poruka){
                assertEquals(1,1);
            }
        };
    }


}