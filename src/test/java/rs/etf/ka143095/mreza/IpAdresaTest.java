package rs.etf.ka143095.mreza;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by aki on 7/6/16.
 */
public class IpAdresaTest {
    String ip;
    @Before
    public void init(){
        ip=new IpAdresa().dobaviteIpadresu();
    }

    @Test
    public void daLiJeDosloDoGreskeUKomunikaciji() {
        Assert.assertNotNull(ip);
    }
    @Test
    public void daLiJeDosloDoGreskeUPovratnojVrednosti() {
        //preuzeto sa: http://www.mkyong.com/regular-expressions/how-to-validate-ip-address-with-regular-expression/
        String IPADDRESS_PATTERN =
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        //kraj preuzetog koda
        Matcher m = pattern.matcher(ip);
        assertTrue(m.matches());

    }
    @Test
    public void testPorta(){
        int port = 65500;
        assertEquals(IpAdresa.PORT, port);
    }
}