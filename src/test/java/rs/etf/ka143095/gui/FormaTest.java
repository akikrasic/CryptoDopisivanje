package rs.etf.ka143095.gui;

import org.junit.Before;
import org.junit.Test;
import rs.etf.ka143095.main.Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

/**
 * Created by aki on 7/6/16.
 */
public class FormaTest {


    private JTextField porukaTF;
    private Forma forma ;
    @Before
    public void setUp() throws Exception {
        Main main = new Main();
        main.setKripto(null);
        main.setServer(null);
        main.setKlijent(null);
        main.setServer(null);
        forma = new Forma(main){
            public void poveziteSeSaServerom(String ip){

            }
            public void posaljitePoruku(String poruka){

            }//ovde se vrsi testiranje iskljucivo forme, tako da metoda koja salje poruku je nasledjena da nema nikakvu funkcionalnost
            //kako ne bi pozivala metode klase Klijent koje vrse slanje poruke kroz mrezu
        };
        porukaTF= forma.getPorukaTF();
    }


    @Test
    public void slanjePorukeUAreu(){
        JTextField porukaTF = forma.getPorukaTF();
        String poruka = "проба123";
        porukaTF.setText(poruka);
        JButton b =forma.getPosaljitePorukuDugme();
        b.doClick();

        poruka="Ви: проба123\n\n";

        String area = forma.getDopisivanjeTA().getText();
        assertEquals(area, poruka);
       /* System.out.println(poruka);
        System.out.println(area);*/

    }
    @Test
    public void slanjePraznePorukeUAreu(){
        JTextField porukaTF = forma.getPorukaTF();
        String poruka = "";
        porukaTF.setText(poruka);
        JButton b =forma.getPosaljitePorukuDugme();
        b.doClick();

        poruka="";

        String area = forma.getDopisivanjeTA().getText();
        assertEquals(area, poruka);


    }
    @Test
    public void cistPorukaTFPosleUnosa(){
       porukaTF.setText("2121523");
        forma.getPosaljitePorukuDugme().doClick();
        assertEquals(porukaTF.getText().trim(), "");
    }

    @Test
    public void dodajteUTextAreu() throws Exception {
        String proba="тест";
        forma.dodajteUTextAreu(proba);
        assertEquals(forma.getDopisivanjeTA().getText().trim(), proba);

    }
    @Test
    public void daLiJeUnetaIspravnaAdresaKlijentaDa(){
        forma.getUnosIpKorisnikaTF().setText("192.168.1.3");
        assertTrue(forma.daLiJeUnetaIspravnaIpAdresaKlijenta(forma.getUnosIpKorisnikaTF().getText()));
    }
    @Test
    public void daLiJeUnetaIspravnaAdresaKlijentaNe(){
        forma.getUnosIpKorisnikaTF().setText("gfcgfcghfcfgtg");
        assertFalse(forma.daLiJeUnetaIspravnaIpAdresaKlijenta(forma.getUnosIpKorisnikaTF().getText()));
    }
    @Test
    public void daLiJeCistaAdresaPosleUnosa(){
        forma.getUnosIpKorisnikaTF().setText("192.168.1.2");
        forma.getPoveziteSeSaKorisnikomDugme().doClick();
        assertTrue(forma.getPorukaTF().getText().equals(""));
    }
    @Test
    public void daLiJeIspravnaIpAdresaLabelDodavanje(){
        String natpisSaForme = forma.getIpAdresaLabel().getText();
        String ip = forma.getIp();
        String natpisZaProveru = String.format("Ваша ИП адреса је: %s .", ip);
        assertEquals(natpisSaForme, natpisZaProveru);
    }
    @Test
    public void daLiJeIspravnaIpAdresaLabelDodavanjeToolTipText(){
       assertEquals(forma.getIpAdresaLabel().getToolTipText(), forma.getToolTipPorukaOAdresi());
    }
    @Test
    public void porukaSagovornikaDodata(){
        String poruka ="Ово је порука коју је послао саговорник";
        forma.dodajtePorukuSagovornika(poruka);
        assertEquals(forma.getDopisivanjeTA().getText().trim(), String.format("Саговорник: %s",poruka));
    }
    @Test
    public void dodavanjePorta(){
       int port  = 65500;
        forma.getPortTF().setText(String.valueOf(port));
        int vrIzPolja = Integer.parseInt(forma.getPortTF().getText().trim());
        assertEquals(port, vrIzPolja);
    }
}