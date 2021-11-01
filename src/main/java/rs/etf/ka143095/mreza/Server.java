package rs.etf.ka143095.mreza;

import rs.etf.ka143095.main.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by aki on 7/6/16.
 */
public class Server implements Runnable  {
    private boolean nijeZavrsenaAplikacija;
    private Main main;
    private int port;
    private ServerSocket server;
    public Server (Main main){
        this.main = main;
        port = IpAdresa.PORT;
    }
    public Server(Main main, int port){
        this.main = main;
        this.port = port;
    }
    public void run(){
        this.nijeZavrsenaAplikacija=true;
        try {
            server = new ServerSocket(port);

            ExecutorService exec = Executors.newCachedThreadPool();


               // main.getKlijent().setVezaSaServerom(prihvat);
                exec.execute(new ServerObradjujeZahtevKlijenta(server.accept(), main));

        }
        catch(Exception e){
            try {
                port+=1;
                 server = new ServerSocket(port);//za 2 pokretanja


                ExecutorService exec = Executors.newCachedThreadPool();

                    exec.execute(new ServerObradjujeZahtevKlijenta(server.accept(), main));

            }
                catch(Exception e1){
                    e.printStackTrace();
                    try {
                        server.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
        }

    }
    public void zatvoriteKonekciju(){
        Thread.currentThread().interrupt();
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public boolean isNijeZavrsenaAplikacija() {
        return nijeZavrsenaAplikacija;
    }

    public void setNijeZavrsenaAplikacija(boolean nijeZavrsenaAplikacija) {
        this.nijeZavrsenaAplikacija = nijeZavrsenaAplikacija;
    }

    public int getPort() {
        return port;
    }


    public void zatvoriteServer(){
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
