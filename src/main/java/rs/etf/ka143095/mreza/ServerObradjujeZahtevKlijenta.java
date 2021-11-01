package rs.etf.ka143095.mreza;

import rs.etf.ka143095.main.Main;
import rs.etf.ka143095.mreza.akcija.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by aki on 7/6/16.
 */
public class ServerObradjujeZahtevKlijenta implements Runnable {
    private Socket socket;
    private Main main;

    private Akcija[] akcije;
    public ServerObradjujeZahtevKlijenta(Socket socket, Main main){
        this.main = main;
        this.socket = socket;

        akcije = new Akcija[]{
                new ZahtevZaUspostavljanjeVeze(main),
                new KriptovanjeSimetricnogKljuca(main),
                new SlanjePoruke(main),
                new ZavrsetakKomunikacije(main, this)

        };
    }

    public void run(){
        Server server = main.getServer();
        while(true){
            try {
                ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

                    obradaPrimljenogTransferObjekta((TransferObjekat) in.readObject());


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
             catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

    public void obradaPrimljenogTransferObjekta(TransferObjekat t){
        akcije[t.id].izvrsiteAkciju(t.poruka);

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }



    public Akcija[] getAkcije() {
        return akcije;
    }

    public void setAkcije(Akcija[] akcije) {
        this.akcije = akcije;
    }
}
