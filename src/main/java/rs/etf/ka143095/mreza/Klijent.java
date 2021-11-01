package rs.etf.ka143095.mreza;

import rs.etf.ka143095.crypto.Kripto;
import rs.etf.ka143095.main.Main;
import rs.etf.ka143095.mreza.akcija.Akcija;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by aki on 7/6/16.
 */
public class Klijent {
    private Main main;
    private Kripto kripto;
    private Socket vezaSaServerom;

    public Klijent(Main main){
        this.main=main;

        kripto = main.getKripto();

    }

    public boolean uspostaviteVezu(String ip, int port){
        try {
            vezaSaServerom = new Socket(ip, port);
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void posaljiteZahtevZaUspostavomVeze(){
        String poruka=null;
        try {
            poruka = String.format("%s/%d/%s", InetAddress.getLocalHost().getHostAddress(), main.getServer().getPort(),kripto.getJavniKljucEnkodovani());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        posaljiteTransferObjekat(Akcija.ZAHTEV_ZA_USPOSTAVLJANJE_VEZE,poruka);

        //new Thread(new ServerObradjujeZahtevKlijenta(vezaSaServerom, main)).start();

    }

    public void posaljiteKriptovaniSimetricniKljuc(){
        posaljiteTransferObjekat(Akcija.KRIPTOVANJE_SIMETRICNOG_KLJUCA, kripto.enkriptujteSimetricniKljucJavnimKljucemSagovornika());
        main.getForma().uspesnoSteSePovezaliNaServer();
    }
    public void posaljitePoruku(String poruka){
        posaljiteTransferObjekat(Akcija.SLANJE_PORUKE, kripto.enkriptujteSimetricnimKljucem(poruka));
        System.out.println("poruka poslata " );
    }
    public void zavrsiteKomunikaciju(){
        posaljiteTransferObjekat(Akcija.ZAVRSETAK_KOMUNIKACIJE, null);
    }



    public void posaljiteTransferObjekat(int idOperacije, String poruka){
        TransferObjekat t = new TransferObjekat();
        t.id=idOperacije;
        t.poruka=poruka;
        posaljite(t);
        System.out.println(t.poruka);
    }


    public void posaljite(TransferObjekat t){

        try {
            System.out.println(vezaSaServerom);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(vezaSaServerom.getOutputStream()));
            out.writeObject(t);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zatvoriteKonekciju(){
        if(vezaSaServerom!=null) {
            try {
                vezaSaServerom.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }



    public Kripto getKripto() {
        return kripto;
    }

    public void setKripto(Kripto kripto) {
        this.kripto = kripto;
    }
}
