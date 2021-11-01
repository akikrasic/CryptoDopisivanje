package rs.etf.ka143095.main;

import rs.etf.ka143095.crypto.Kripto;
import rs.etf.ka143095.gui.Forma;
import rs.etf.ka143095.mreza.IpAdresa;
import rs.etf.ka143095.mreza.Klijent;
import rs.etf.ka143095.mreza.Server;
import rs.etf.ka143095.mreza.TransferObjekat;

import javax.swing.*;

/**
 * Created by aki on 7/6/16.
 */

public class Main {
    private Forma forma;
    private Klijent klijent;
    private Server server;
    private Kripto kripto;
    public Main(){
        kripto = new Kripto(this);
        server = new Server(this);
        klijent = new Klijent(this);
        forma= new Forma(this);


    }
    public Main(int noviPort){
        kripto = new Kripto(this);

        server = new Server(this, noviPort);
        klijent = new Klijent(this);
        forma= new Forma(this);
    }
    public void  izvrsavanjeAplikacije(){
        new Thread(server ).start();
        SwingUtilities.invokeLater(()->{
           forma.setVisible(true);
        });
    }
    public static void main(String[] args){
        Main main = new Main();
        main.izvrsavanjeAplikacije();

    }


    public Kripto getKripto() {
        return kripto;
    }

    public void setKripto(Kripto kripto) {
        this.kripto = kripto;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Klijent getKlijent() {
        return klijent;
    }

    public void setKlijent(Klijent klijent) {
        this.klijent = klijent;
    }

    public Forma getForma() {
        return forma;
    }

    public void setForma(Forma forma) {
        this.forma = forma;
    }
}
