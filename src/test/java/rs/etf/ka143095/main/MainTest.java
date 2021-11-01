package rs.etf.ka143095.main;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aki on 7/7/16.
 */
public class MainTest {
    private Main main;
    @Before
    public void init(){
        main = new Main();
    }
    @Test
    public void notNull(){
        assertNotNull(main.getForma());
        assertNotNull(main.getKlijent());
        assertNotNull(main.getServer());
        assertNotNull(main.getKripto());
    }
    @Test
    public void notNullDrugiKonstruktor(){
        int port = 45000;
        main = new Main(port);
        assertNotNull(main.getForma());
        assertNotNull(main.getKlijent());
        assertNotNull(main.getServer());
        assertNotNull(main.getKripto());
    }
    @Test
    public void daLiJeServerDobioNoviPort(){
        int port = 45000;
        main = new Main(port);
        assertEquals(main.getServer().getPort(), port);
    }

}