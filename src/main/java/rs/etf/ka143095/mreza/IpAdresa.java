package rs.etf.ka143095.mreza;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by aki on 7/6/16.
 */
public class IpAdresa {
    public String dobaviteIpadresu(){
        String ip=null;
        try {
            URL url = new URL("http://checkip.amazonaws.com/");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            ip=reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return ip;
    }
    public static final int PORT =65500;
    public static final String DEFAULT_IP_ZA_TESTIRANJE="192.168.1.4";
}
