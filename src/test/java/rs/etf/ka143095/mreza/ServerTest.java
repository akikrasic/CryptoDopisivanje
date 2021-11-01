package rs.etf.ka143095.mreza;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * Created by aki on 7/7/16.
 */
public class ServerTest {

    @Test
    public void probaPorta()  {
        try {
            ServerSocket server = new ServerSocket(IpAdresa.PORT);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}